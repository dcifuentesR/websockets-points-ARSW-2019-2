package edu.eci.arsw.collabpaint.handlers;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.eci.arsw.collabpaint.model.Point;
import edu.eci.arsw.collabpaint.model.Polygon;

@Controller
public class STOMPMessagesHandler {
	
	private ConcurrentHashMap<String,Deque<Polygon>> channels = new ConcurrentHashMap<>();
	
	@Autowired
	SimpMessagingTemplate msgTemplate;
	
	@MessageMapping("newpoint.{canvasId}")
	public void handlePointEvent(Point pt,@DestinationVariable String canvasId) throws Exception{
		if(!channels.containsKey(canvasId))
			channels.put(canvasId, new ConcurrentLinkedDeque<>());
		
		ConcurrentLinkedDeque<Polygon> canvasToEdit = (ConcurrentLinkedDeque<Polygon>) channels.get(canvasId);
		
		if(canvasToEdit.isEmpty() || canvasToEdit.peek().isComplete()) {
			Polygon current = new Polygon();
			 canvasToEdit.push(current);
		}
		
		canvasToEdit.peek().addPoint(pt);
		System.out.println("New point recieved at:"+pt);
		msgTemplate.convertAndSend("/topic/newpoint."+canvasId,pt);
		
		if(canvasToEdit.peek().isComplete()) {
			System.out.println("New polygon created!");
			msgTemplate.convertAndSend("/topic/newpolygon."+canvasId,canvasToEdit.peek());
		}
	}
	
	

}
