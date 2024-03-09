
public class Processor 
{
	Word PC;//a variable to remember what instruction we are working on. 
	Word SP;//stack (a pointer into memory)
    Word Current_Instruction;//The instruction must go into another member Word – “currentInstruction”.
    Word Register[];
	Bit haulted;//The variable that stops while loops in the run 
	Word RD;
	Word function;
	Word RS1; 
	Word RS2;
	Word Immediate;
	Word OP;
	Word new_word;
	MainMemory mm;

	int Current_register;

	  public  Processor()
	  {
		PC= new Word();
		PC.set(0);
		SP= new Word();
		SP.set(1024);
		Current_Instruction= new Word();
		RD= new Word();
		function= new Word();
		RS1= new Word();
		RS2= new Word();
		OP=new Word();
		Immediate= new Word();
		new_word= new Word();
		Register = new Word[32];
		for(int i=0;i<31;i++)
		{
			Register[i]= new Word();
		}
		Register[0].set(0);
		haulted=new Bit(false);
      }
	  /**
	   * while ( not halted)
	fetch an instruction from memory
	get the data for the instruction (decode)
	execute the instruction
	store the results

	   */
	  public void run()
	  {
		  while(!haulted.getValue())
		  {
			  Fetch();
			  decode();
			  execte();
			  store();
		  }
	  }
	private void store() 
	{
		// TODO Auto-generated method stub
		if(Current_register==0)
		{
		 //Do nothing as register at 0 is hard coded to false
			System.out.println("The processor is haulted");
		}
		else if(Current_register==2)
		{
			Register[(int) RD.getUnsigned()].copy(new_word);
			System.out.println("The Register "+ RD.getUnsigned()+" has Stored "+new_word.getSigned());

		}
		else if(Current_register==3)
		{
			Register[(int) RD.getUnsigned()].copy(new_word);
			System.out.println("The Register "+ RD.getUnsigned()+" has Stored "+new_word.getUnsigned());

		}
		else 
		{
			Register[(int) RD.getUnsigned()].copy(new_word);
			System.out.println("The Register "+ RD.getUnsigned()+" has Stored "+new_word.getUnsigned());

			
		}
		
		
	}
	private void execte() 
	{
		// TODO Auto-generated method stub
		if(Current_register==0)
		{
			if(!Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())
			{
				haulted.set(true);
			}
		}
		else if(Current_register==2)
		{
			if(!Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&&!Current_Instruction.getBit(27).getValue())
			{
				ALU mop= new ALU(Register[(int) RD.getUnsigned()], Register[(int) RS2.getUnsigned()]);
				Bit op[]= new Bit[] {function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)};
				mop.doOperation(op);
				new_word.copy(mop.Result);
			}
		}
		else if(Current_register==3)
		{
			if(!Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&&!Current_Instruction.getBit(27).getValue())
			{
				ALU mop= new ALU(Register[(int) RS1.getUnsigned()], Register[(int) RS2.getUnsigned()]);
				Bit op[]= new Bit[] {function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)};
				mop.doOperation(op);
				new_word.copy(mop.Result);
			}
		}
		else 
		{
			if(!Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&&!Current_Instruction.getBit(27).getValue())
			{
				new_word.copy(Immediate);
			}
		}
	}
	private void decode() 
	{
		if(!Current_Instruction.getBit(31).getValue())
		{
			if(!Current_Instruction.getBit(30).getValue())//R0
			{
				Current_register=0;
				Immediate.copy(Current_Instruction);
				OP.copy(Current_Instruction);
				for(int i=0;i<32;i++)
				{
					if(i<(31-4))
					{
			    OP.setBit(i, new Bit(false));
					}
				}
				Immediate=Immediate.rightShift(5);
				
			}
			else//R2
			{
				Current_register=2;
				OP.copy(Current_Instruction);
				RD.copy(Current_Instruction);
				RD=RD.rightShift(5);
				function.copy(RD);
				function= function.rightShift(5);
				RS2.copy(function);
				RS2=RS2.rightShift(4);
				Immediate.copy(RS2);
				Immediate=Immediate.rightShift(5);
				for(int i=0;i<32;i++)
				{
					if(i<(31-4))
					{
					OP.setBit(i, new Bit(false));
					RD.setBit(i, new Bit(false));
					RS2.setBit(i, new Bit(false));
					}
					if(i<28)
					{
					function.setBit(i, new Bit(false));
					}
					if(i<(31-12))
					{
						Immediate.setBit(i,  new Bit(false));
					}
				}
			}
		}
		else
		{
			if(Current_Instruction.getBit(30).getValue())//R3
			{

				Current_register=3;
				OP.copy(Current_Instruction);
				RD.copy(Current_Instruction);
				RD=RD.rightShift(5);
				function.copy(RD);
				function= function.rightShift(5);
				RS2.copy(function);
				RS2=RS2.rightShift(4);
				RS1.copy(RS2);
				RS1=RS1.rightShift(5);
				Immediate.copy(RS1);
				Immediate=Immediate.rightShift(5);
				for(int i=0;i<32;i++)
				{
					if(i<(31-4))
					{
					OP.setBit(i, new Bit(false));
					RD.setBit(i, new Bit(false));
					RS2.setBit(i, new Bit(false));
					RS1.setBit(i, new Bit(false));
					
					}
					if(i<28)
					{
					function.setBit(i, new Bit(false));
					}
					if(i<(31-8))
					{
						Immediate.setBit(i,  new Bit(false));
					}
				}
			}
			else//R1 (DEST Only)
			{
				Current_register=1;
				OP.copy(Current_Instruction);
				RD.copy(Current_Instruction);
				RD=RD.rightShift(5);
				function.copy(RD);
				function= function.rightShift(5);
				
				Immediate.copy(function);
				Immediate=Immediate.rightShift(4);
				for(int i=0;i<32;i++)
				{
					if(i<(31-5))
					{
				    OP.setBit(i, new Bit(false));
					RD.setBit(i, new Bit(false));
					if(i!=(31-5))
					function.setBit(i, new Bit(false));
					}
					if(i<(31-18))
					{
						Immediate.setBit(i,  new Bit(false));
					}
				}
			}
		}
	}
	/**
	 * Get the next instruction from memory (based on the PC). 
	 */
	/**
	 * We facth the current instruction from the main memory and then increase PC so that we can get to the next instruction
	 */
	private void Fetch() {
		// TODO Auto-generated method stub
		Current_Instruction.set(mm.read(PC).getSigned());
        PC.increment();
	}
}

