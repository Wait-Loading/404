import java.io.IOException;

import org.junit.jupiter.api.Test;

public class test_Assembler {

		@Test
		void test_bit() throws IOException
		{
			Assembler Assembler= new Assembler("math 5 R1\r\n"
					+ "math add R1 R1 R2\r\n"
					+ "math add R2 R2\r\n"
					+ "math add R2 R1 R3\r\n"
					+ "halt\r\n"
					+ "");
			for(int i=0;i<Assembler.tokens.size();i++)
			{
				System.out.print(Assembler.tokens.get(i)+ " ");
			}
			Processor pro= new Processor();// A processor constructor 
			String[] instructions= new String[Assembler.parser.instructions.size()];
			for(int i=0;i<instructions.length;i++)
			{
				instructions[i]=Assembler.parser.instructions.get(i);
			}
			pro.mm.load(instructions);// Load the instructions into the main memory 
			pro.run();
			
			
			System.out.println("\n\n\n\n The second Program: \n\n\n\n");
			 Assembler= new Assembler("push add 20 R1\r\n"
			 		+ "push add 0 R1\r\n"
			 		+ "push add 1 R1\r\n"
			 		+ "push add 1 R1\r\n"
			 		+ "pop R1\r\n"
			 		+ "pop R4\r\n"
			 		+ "pop R3\r\n"
			 		+ "pop R2\r\n"
			 		+ "branch 4  greaterOrEqual R1 R2 \r\n"
			 		+ "math add R1 R3 R1\r\n"
			 		+ "math add R0 R4 R3\r\n"
			 		+ "math add R0 R1 R4\r\n"
			 		+ "jump 8\r\n"
			 		+ "push add 0 R1\r\n"
			 		+ "halt\r\n"
			 		+ "");
		
			 
			 pro= new Processor();// A processor constructor 
			 instructions= new String[Assembler.parser.instructions.size()];
			for(int i=0;i<instructions.length;i++)
			{
				instructions[i]=Assembler.parser.instructions.get(i);
			}
			pro.mm.load(instructions);// Load the instructions into the main memory 
			pro.run();
		
		
		
		System.out.println("\n\n\n\n The third Program: \n\n\n\n");
		 Assembler= new Assembler("push add 20 R1\r\n"
		 		+ "push add 0 R1\r\n"
		 		+ "push add 1 R1\r\n"
		 		+ "push add 1 R1\r\n"
		 		+ "pop R1\r\n"
		 		+ "pop R4\r\n"
		 		+ "pop R3\r\n"
		 		+ "pop R2\r\n"
		 		+ "call 4  greaterOrEqual R1 R2 \r\n"
		 		+ "math add R1 R3 R1\r\n"
		 		+ "math add R0 R4 R3\r\n"
		 		+ "math add R0 R1 R4\r\n"
		 		+ "jump 8\r\n"
		 		+ "push add 0 R1\r\n"
		 		+"push add 32 R0\r\n"
		 		+"return\r\n"
		 		+"store 12 R1\r\n"
		 		+ "halt\r\n"
		 		+ "");
		
		 pro= new Processor();// A processor constructor 
		 instructions= new String[Assembler.parser.instructions.size()];
		for(int i=0;i<instructions.length;i++)
		{
			instructions[i]=Assembler.parser.instructions.get(i);
		}
		pro.mm.load(instructions);// Load the instructions into the main memory 
		pro.run();
	}
}



