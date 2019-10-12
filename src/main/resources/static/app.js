var app = (function () {

    class Point{
        constructor(x,y){
            this.x=x;
            this.y=y;
        }        
    }
    
    var stompClient = null;

    var addPointToCanvas = function (point) {        
        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        ctx.beginPath();
        ctx.arc(point.x, point.y, 3, 0, 2 * Math.PI);
        ctx.stroke();
    };
    
    var addPolygonToCanvas = function(polygon){
    	var canvas = document.getElementById("canvas");
    	var ctx = canvas.getContext("2d");
    	ctx.fillStyle = "#f00"
    	ctx.beginPath();
    	ctx.moveTo(polygon.points[0].x,polygon.points[0].y);
    	polygon.points.forEach(function(currentPoint){
    		ctx.lineTo(currentPoint.x,currentPoint.y);
    	});
    	ctx.closePath();
    	ctx.fill();
    	ctx.stroke();
    };
    
    var getMousePosition = function (evt) {
        canvas = document.getElementById("canvas");
        var rect = canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    };


    var connectAndSubscribe = function (canvasId) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        
        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/newpoint.'+canvasId, function (eventbody) {
                
                var jsObject=JSON.parse(eventbody.body);
                var pt=new Point(jsObject.x,jsObject.y);
                console.info("recieved point at "+JSON.stringify(pt));
                addPointToCanvas(pt);
                
            });
            
            stompClient.subscribe('/topic/newpolygon.'+canvasId,function(eventbody){
            	var jsObject = JSON.parse(eventbody.body);
            	console.info("created new polygon!");
            	addPolygonToCanvas(jsObject);
            });
        });

    };
    
    

    return {

        init: function () {
            var can = document.getElementById("canvas");
            
            if(window.PointerEvent){
            	can.addEventListener("pointerdown",function(event){
            		var point = getMousePosition(event);
            		app.publishPoint(point.x ,point.y ,$("#canvasId").val())
            	})
            }
            //websocket connection
            //connectAndSubscribe();
        },

        publishPoint: function(px,py,canvasId){
            var pt=new Point(px,py);
            console.info("publishing point at "+JSON.stringify(pt));
            
            stompClient.send("/app/newpoint."+canvasId,{},JSON.stringify(pt));

            //publicar el evento
        },

        disconnect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        },
        connectAndSubscribe:connectAndSubscribe
    };

})();