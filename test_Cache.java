import java.io.IOException;

import org.junit.jupiter.api.Test;

public class test_Cache {
	@Test
	void test_Sum20Ints() throws IOException
	{
		System.out.println("\n\n\n\n\n\nThe sum of 20 int in an array and add them: ");

		Assembler Assembler= new Assembler("push add 1 R1\r\n"
				+ "push add 2 R1\r\n"
				+ "push add 3 R1\r\n"
				+ "push add 4 R1\r\n"
				+ "push add 5 R1\r\n"
				+ "push add 6 R1\r\n"
				+ "push add 7 R1\r\n"
				+ "push add 8 R1\r\n"
				+ "push add 9 R1\r\n"
				+ "push add 10 R1\r\n"
				+ "push add 11 R1\r\n"
				+ "push add 12 R1\r\n"
				+ "push add 13 R1\r\n"
				+ "push add 14 R1\r\n"
				+ "push add 15 R1\r\n"
				+ "push add 16 R1\r\n"
				+ "push add 17 R1\r\n"
				+ "push add 18 R1\r\n"
				+ "push add 19 R1\r\n"
				+ "push add 20 R1\r\n"
				+ "pop R31\r\n"
				+ "pop R30\r\n"
				+ "pop R29\r\n"
				+ "pop R28\r\n"
				+ "pop R27\r\n"
				+ "pop R26\r\n"
				+ "pop R25\r\n"
				+ "pop R24\r\n"
				+ "pop R23\r\n"
				+ "pop R22\r\n"
				+ "pop R21\r\n"
				+ "pop R20\r\n"
				+ "pop R19\r\n"
				+ "pop R18\r\n"
				+ "pop R17\r\n"
				+ "pop R16\r\n"
				+ "pop R15\r\n"
				+ "pop R14\r\n"
				+ "pop R13\r\n"
				+ "pop R12\r\n"
				+ "math add R31 R30 R1 \r\n"
				+ "math add R29 R1 \r\n"
				+ "math add R28 R1 \r\n"
				+ "math add R27 R1 \r\n"
				+ "math add R26 R1 \r\n"
				+ "math add R25 R1 \r\n"
				+ "math add R24 R1 \r\n"
				+ "math add R23 R1 \r\n"
				+ "math add R22 R1 \r\n"
				+ "math add R21 R1 \r\n"
				+ "math add R20 R1 \r\n"
				+ "math add R19 R1 \r\n"
				+ "math add R18 R1 \r\n"
				+ "math add R17 R1 \r\n"
				+ "math add R16 R1 \r\n"
				+ "math add R15 R1 \r\n"
				+ "math add R14 R1 \r\n"
				+ "math add R13 R1 \r\n"
				+ "math add R12 R1 \r\n"
				+ "halt\r\n"
				+ "");
		Processor pro= new Processor();// A processor constructor 
		String[] instructions= new String[Assembler.parser.instructions.size()];
		for(int i=0;i<instructions.length;i++)
		{
			instructions[i]=Assembler.parser.instructions.get(i);
		}
		pro.mm.load(instructions);// Load the instructions into the main memory 
		pro.run();
	}

	@Test
	void test_LinkedList() throws IOException
	{
		System.out.println("\n\n\n\n\n\nThe linked list:");
		Assembler Assembler= new Assembler("push add 1 R1\r\n"
				+ "push add 1021 R1\r\n"
				+ "push add 2 R1\r\n"
				+ "push add 1019 R1\r\n"
				+ "push add 3 R1\r\n"
				+ "push add 1017 R1\r\n"
				+ "push add 4 R1\r\n"
				+ "push add 1015 R1\r\n"
				+ "push add 5 R1\r\n"
				+ "copy 1 R3\r\n"
				+ "copy 5 R4\r\n"
				+ "copy 0 R5\r\n"
				+ "copy 2 R21\r\n"
				+ "copy 1023 R15\r\n"
				+ "copy 1022 R14\r\n"
				+ "load 0 R15 R1\r\n"
				+ "load 0 R14 R31\r\n"
				+ "load 0 R31 R2\r\n"
				+ "math add R3 R5\r\n"
				+ "branch 1 greaterOrEqual R5 R4\r\n"
				+ "jump 15\r\n"
				+ "math subtract R21 R14 \r\n"
				+ "halt\r\n"
				+ "");
		Processor pro= new Processor();// A processor constructor 
		String[] instructions= new String[Assembler.parser.instructions.size()];
		for(int i=0;i<instructions.length;i++)
		{
			instructions[i]=Assembler.parser.instructions.get(i);
		}
		pro.mm.load(instructions);// Load the instructions into the main memory 
		pro.run();
	}
	@Test
	void test_Sum20Ints_reverse() throws IOException
	{
		System.out.println("\n\n\n\n\n\nThe sum of 20 int in an array and add them in reverse:");

		Assembler Assembler= new Assembler("push add 1 R1\r\n"
				+ "push add 2 R1\r\n"
				+ "push add 3 R1\r\n"
				+ "push add 4 R1\r\n"
				+ "push add 5 R1\r\n"
				+ "push add 6 R1\r\n"
				+ "push add 7 R1\r\n"
				+ "push add 8 R1\r\n"
				+ "push add 9 R1\r\n"
				+ "push add 10 R1\r\n"
				+ "push add 11 R1\r\n"
				+ "push add 12 R1\r\n"
				+ "push add 13 R1\r\n"
				+ "push add 14 R1\r\n"
				+ "push add 15 R1\r\n"
				+ "push add 16 R1\r\n"
				+ "push add 17 R1\r\n"
				+ "push add 18 R1\r\n"
				+ "push add 19 R1\r\n"
				+ "push add 20 R1\r\n"
				+ "pop R12\r\n"
				+ "pop R13\r\n"
				+ "pop R14\r\n"
				+ "pop R15\r\n"
				+ "pop R16\r\n"
				+ "pop R17\r\n"
				+ "pop R18\r\n"
				+ "pop R19\r\n"
				+ "pop R20\r\n"
				+ "pop R21\r\n"
				+ "pop R22\r\n"
				+ "pop R23\r\n"
				+ "pop R24\r\n"
				+ "pop R25\r\n"
				+ "pop R26\r\n"
				+ "pop R27\r\n"
				+ "pop R28\r\n"
				+ "pop R29\r\n"
				+ "pop R30\r\n"
				+ "pop R31\r\n"
				+ "math add R31 R30 R1 \r\n"
				+ "math add R29 R1 \r\n"
				+ "math add R28 R1 \r\n"
				+ "math add R27 R1 \r\n"
				+ "math add R26 R1 \r\n"
				+ "math add R25 R1 \r\n"
				+ "math add R24 R1 \r\n"
				+ "math add R23 R1 \r\n"
				+ "math add R22 R1 \r\n"
				+ "math add R21 R1 \r\n"
				+ "math add R20 R1 \r\n"
				+ "math add R19 R1 \r\n"
				+ "math add R18 R1 \r\n"
				+ "math add R17 R1 \r\n"
				+ "math add R16 R1 \r\n"
				+ "math add R15 R1 \r\n"
				+ "math add R14 R1 \r\n"
				+ "math add R13 R1 \r\n"
				+ "math add R12 R1 \r\n"
				+ "halt\r\n"
				+ "");
		Processor pro= new Processor();// A processor constructor 
		String[] instructions= new String[Assembler.parser.instructions.size()];
		for(int i=0;i<instructions.length;i++)
		{
			instructions[i]=Assembler.parser.instructions.get(i);
		}
		pro.mm.load(instructions);// Load the instructions into the main memory 
		pro.run();
	}

}
