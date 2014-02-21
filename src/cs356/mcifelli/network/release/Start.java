package cs356.mcifelli.network.release;

import cs356.mcifelli.network.common.Node;
import cs356.mcifelli.network.common.NodeHandler;

public class Start {
	public static void main(String[] args) {
		Node node = new Node(new NodeHandler(), "test-server", 9007);
		
		if (args.length < 1) {
			node.Connect("localhost");
			//node.Disconnect("localhost");
		} else {
			new Thread(node).start();
		}
	}
}
