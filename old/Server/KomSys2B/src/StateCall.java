
public class StateCall extends State{
	public String getStateName(){return "StateCall";}
	public State startMessageSendRec(){return new StateClose(null, null, null, 0);}
}
