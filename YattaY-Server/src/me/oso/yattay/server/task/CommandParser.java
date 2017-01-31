package me.oso.yattay.server.task;

/**
 * Created by Thomas on 6 oct. 2016
 */
public class CommandParser {

	/**
	 * Converts a net command to an executable task
	 * @param cmd the string command
	 * @return new task or null if <b>cmd</b> was incorrect
	 */
	public static Task ParseNetwork(String cmd) {
		byte opcode = cmd.getBytes()[0];
		String[] args = cmd.substring(1).split(";");
		for(TaskType type : TaskType.values()) {
			if(type.getOpcode() == (opcode & 0xFF)) {
				return new Task(type, args);
			}
		}
		return null;
	}
	
	public static Task ParseInput(String cmd) {
		return null;
	}
}
