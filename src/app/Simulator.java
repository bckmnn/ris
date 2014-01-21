package app;


import static app.nodes.NodeFactory.nodeFactory;
import static app.vecmathimp.FactoryDefault.vecmath;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import akka.actor.UntypedActor;
import app.eventsystem.CameraCreation;
import app.eventsystem.NodeCreation;
import app.eventsystem.NodeModification;
import app.eventsystem.PhysicModification;
import app.eventsystem.SimulateCreation;
import app.eventsystem.Types;
import app.messages.KeyDef;
import app.messages.KeyState;
import app.messages.Message;
import app.messages.Mode;
import app.messages.SimulateType;
import app.nodes.Node;
import app.vecmath.Vector;
import app.vecmathimp.MatrixImp;

public class Simulator extends UntypedActor {
    
    private Map<String, Node> nodes = new HashMap<String, Node>();
    private Map<Node, KeyDef> simulations=new HashMap<Node, KeyDef>();
    private Set<Integer> pressedKeys = new HashSet<Integer>();
    private Set<Integer> releasedKeys = new HashSet<Integer>();
    private Set<Integer> toggeled=new HashSet<Integer>();
	private float angle = 0;
    
    private void initialize() {
        getSender().tell(Message.INITIALIZED, self());
    }

    private void simulate() throws Exception {
    	for(Map.Entry<Node, KeyDef> entry:simulations.entrySet()){
    		Set<Integer> keys=entry.getValue().getKeys();
    		if(keys==null||keys.isEmpty()){
    			doSimulation(entry.getKey(), entry.getValue().getType(), entry.getValue().getVector());
    		}else{
    			if(entry.getValue().getMode()==Mode.DOWN){
    				boolean contains=false;
    				for(Integer i:keys)if(pressedKeys.contains(i))contains=true;
    				if(contains)doSimulation(entry.getKey(), entry.getValue().getType(), entry.getValue().getVector());
    			}else if(entry.getValue().getMode()==Mode.TOGGLE){
    				boolean contains=false;
    				for(Integer i:keys)if(toggeled.contains(i))contains=true;
    				if(contains)doSimulation(entry.getKey(), entry.getValue().getType(), entry.getValue().getVector());
    			}else{
    				throw new Exception("Add Key Mode!");
    			}
    		}
    	}
                
        getSender().tell(Message.DONE, self());
    }
    
    private void doSimulation(Node node, SimulateType type, Vector vec){
    	//StopWatch sw=new StopWatch();
    	if(type==SimulateType.ROTATE){
    		//TODO: Rotate simulation
    		angle= 0.5f;
    		node.updateWorldTransform(vecmath.rotationMatrix(vec.x(), vec.y(),vec.z(), angle));
			angle = 0;
			getSender().tell(new NodeModification(node.id,node.getWorldTransform()), self());
    	}
    	else if(type==SimulateType.TRANSLATE){
    		node.updateWorldTransform(MatrixImp.translate(vec));
    		getSender().tell(new NodeModification(node.id,node.getWorldTransform()), self());
    	}
    	
    	//st end nodemodification
    }
    
    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Message.LOOP) {
        	System.out.println("simulation loop");
            simulate();
        } else if (message == Message.INIT) {
            initialize();
        } else if(message instanceof KeyState){
        	pressedKeys.clear();
        	releasedKeys.clear();
        	pressedKeys.addAll(((KeyState)message).getPressedKeys());
        	releasedKeys.addAll(((KeyState)message).getReleasedKeys());
        	for(Integer ik:releasedKeys){
        		if(toggeled.contains(ik))toggeled.remove(ik);
        		else toggeled.add(ik);
        	}
        }
        
//        else if (message instanceof NodeCreation) {
//        	System.out.println("NodeCreation");
//        	
//        	if (((NodeCreation) message).type == Types.GROUP) {
//        		Node newNode = nodeFactory.groupNode(((NodeCreation) message).id);
//        		nodes.put(newNode.id, newNode);
//        	} else if (((NodeCreation) message).type == Types.CUBE) {
//        		
//        		System.out.println("Shadering cube with " + ((NodeCreation) message).shader);
//        		
//        		
//        		Node newNode = nodeFactory.cube(((NodeCreation) message).id, ((NodeCreation) message).shader);
//        		nodes.put(newNode.id, newNode);
//        	}
//        	
//        	
//        } else if (message instanceof CameraCreation) {
//        	System.out.println("CameraCreation");
//        	
//        	Camera camera = nodeFactory.camera(((CameraCreation) message).id);
//        	nodes.put(((CameraCreation) message).id, camera);
//        	
//        } 
        	else if (message instanceof NodeModification) {
//        	System.out.println("NodeModification");
        	
        	System.out.println("Nodes " + nodes);
        	System.out.println("Accesing " + ((NodeModification) message).id);
        	if(nodes.containsKey(((NodeModification) message).id)){
        		Node modify = nodes.get(((NodeModification) message).id);
        		System.out.println("haaaooooooooooooooooooooooooooooooooooooooooo\n"+modify.id+"\n"+"local\n"+modify.getLocalTransform()+"world\n"+modify.getWorldTransform());
        		if (((NodeModification) message).localMod != null) {
        			 modify.setLocalTransform(((NodeModification) message).localMod);
    				 modify.updateWorldTransform();
        			System.out.println("haaaooooooooooooooooooooooooooooooooooooooooo\n"+modify.id+"\n"+"local\n"+modify.getLocalTransform()+"world\n"+modify.getWorldTransform());
        		}
//        		if (((NodeModification) message).appendTo != null) {
//        			
//        			System.out.println("Appending " + ((NodeModification) message).id + " to " + ((NodeModification) message).appendTo);
//        			
//        			
//        			modify.appendTo(nodes.get(((NodeModification) message).appendTo));
//        		}//cause error on run, delets simulations
        	}
        } 
//        else if (message instanceof StartNodeModification) {
//        	System.out.println("StartNodeModification");
//        	
//        	Node start = nodes.get(((StartNodeModification) message).id);
//        }
        else if(message instanceof SimulateCreation){
        	SimulateCreation sc=(SimulateCreation)message;
        	Node newNode=null;
        	if (((NodeCreation) message).type == Types.GROUP) {
        		newNode = nodeFactory.groupNode(((NodeCreation) message).id);
        		nodes.put(newNode.id, newNode);
        	} else if (((NodeCreation) message).type == Types.CUBE) {
        		
        		System.out.println("Shadering cube with " + ((NodeCreation) message).shader);
        		
        		
        		newNode = nodeFactory.cube(((NodeCreation) message).id, ((NodeCreation) message).shader);
        		nodes.put(newNode.id, newNode);
        	}else if(((NodeCreation) message).type == Types.CAMERA){
        		newNode = nodeFactory.camera(((CameraCreation) message).id);
        		nodes.put(((CameraCreation) message).id, newNode);
        	}
        	else{
        		throw new Exception("Please implement Type");
        	}
        	if(sc.getSimulation()!=SimulateType.NONE){
        		System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaalllllllllllllllllllooooooooo\n"+newNode.id+sc.getSimulation()+"\n"+"local\n"+newNode.getLocalTransform()+"world\n"+newNode.getWorldTransform()+"keys"+sc.getKeys());
        		simulations.put(newNode, new KeyDef(sc.getSimulation(), sc.getKeys(), sc.getMode(), sc.getVector()));
        		newNode.setLocalTransform(sc.modelmatrix);
        		newNode.updateWorldTransform(); //TODO: Node klasse fixen.... was geht denn hier
//        		System.out.println("simulations\n"+simulations.get(newNode).getVector()+"\n"+simulations.isEmpty()+sc.getSimulation());
        		
        	}else{
        		simulations.remove(newNode);
        	}
        }
        else if (message instanceof PhysicModification) {
//        	System.out.println("Physic data received!!!!!!!!!!!!!" + (((PhysicModification) message)).velocity);
        	
        	
        }
        
        
    }

	
}