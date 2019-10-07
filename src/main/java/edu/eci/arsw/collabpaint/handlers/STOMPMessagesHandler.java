package edu.eci.arsw.collabpaint.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.eci.arsw.collabpaint.model.Point;

@Controller
public class STOMPMessagesHandler {
	
	@Autowired
	SimpMessagingTemplate msgTemplate;
	
	//--------change this for testing
	@MessageMapping(value="/newpoint")
	public void handlePointEvent(Point pt,@DestinationVariable String canvasId) {
		System.out.println("New point recieved at:"+pt);
		msgTemplate.convertAndSend("/topic/newpoint."+canvasId,pt);
	}

}
