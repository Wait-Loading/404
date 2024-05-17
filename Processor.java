
public class Processor 
{
	Word PC;//a variable to remember what instruction we are working on. 
	Word SP;//stack (a pointer into memory)
	Word Current_Instruction;//The instruction must go into another member Word – “currentInstruction”.
	Word Register[];// The array of 32 registers 
	Bit haulted;//The variable that stops while loops in the run 
	Word RD;//The REGISTER DESTINATION
	Word function;// The function word
	Word RS1; //The register 1 word
	Word RS2;//The register 2 word
	Word Immediate; //The Immediate word
	Word OP; //The OP code
	Word new_word;//The new word to store the return of the execute
	MainMemory mm;// The main memory constructor
	InstructionCache cache;
	L2Cache L2;
	static int CurrentClockCycle;
	OPERATION Operation;
	public enum OPERATION{
		Math ,Branch ,Call, Push ,Load,Store,POP
	}
	int Current_register;// The value of the current register

	public  Processor()
	{
		L2= new L2Cache();
		mm = new MainMemory();
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
		for(int i=0;i<32;i++)
		{
			Register[i]= new Word();
		}
		Register[0].set(0);
		haulted=new Bit(false);
		CurrentClockCycle=0;
		cache= new InstructionCache(L2,true);//The L2 cache is connected in the cache with indicator where to use it or not.
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
	/**
	 * Updating the current register destination
	 */
	private void store() 
	{
		if(Operation==OPERATION.Math)
		{
			// TODO Auto-generated method stub
			if(Current_register==0)
			{
				//Do nothing as register at 0 is hard coded to false
				System.out.println("The processor is haulted");
				System.out.println("THE NUMBER OF CLOCK CYCLES FOR ABOVE PROCESSING : " + CurrentClockCycle);

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
		else if(Operation==OPERATION.Branch)
		{
			PC.copy(new_word);
			System.out.println("BRANCHED to PC : "+ PC.getUnsigned());
		}
		else if(Operation==OPERATION.Call)
		{
			PC.copy(new_word);
			System.out.println("CALLED to PC : "+ PC.getUnsigned());
		}
		else if(Operation==OPERATION.Push)
		{

		}
		else if(Operation==OPERATION.Load &&Current_register!=0)
		{
			Register[(int) RD.getUnsigned()].copy(new_word);
			System.out.println("The Register "+ RD.getUnsigned()+" has Stored "+new_word.getUnsigned());

		}
		else if(Operation==OPERATION.Load )
		{
			PC.copy(new_word);
			System.out.println("The PC is chnages to  "+new_word.getUnsigned());

		}
		else if(Operation==OPERATION.POP)
		{
			Register[(int) RD.getUnsigned()].copy(new_word);
			System.out.println("The Register "+ RD.getUnsigned()+" has Stored "+new_word.getUnsigned());
		}
	}
	/**
	 * For this assignment, we will only consider the Math opcode (000). 
	 * Execution is simply copying the data into the ALU for this opcode. 
	 * Note the “Function” portion of the instructions. 
	 * That is the same 4 bits as the ALU requires for indicating which instruction to execute. 
	 * Add an ALU to the processor.
	 * exampleP: When the opcode is 000??, set OP1 and OP2 on the ALU appropriately and then call doOp passing the 4 function bits. 
	 * The result from the ALU is the result of execution 
	 */
	private void execte() 
	{
		// TODO Auto-generated method stub
		if(Current_register==0)
		{
			if(!Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())//MATH HALT
			{
				Operation= OPERATION.Math;
				haulted.set(true);
			}
			else if(Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())//BRANCH JUMP: pc <- imm
			{
				Operation= OPERATION.Branch;
				PC.copy(Immediate);
				new_word.copy(Immediate);
			}
			else if(!Current_Instruction.getBit(29).getValue()&&Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())//CALL push pc; pc <- imm
			{
				Operation= OPERATION.Call;

				push(PC);

				new_word.copy(Immediate);

			}
			else if(!Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& Current_Instruction.getBit(27).getValue())//LOAD RETURN (pc <- pop)
			{
				Operation= OPERATION.Load;

				new_word.copy(pop());
			}
			else if(!Current_Instruction.getBit(29).getValue()&&Current_Instruction.getBit(28).getValue()&& Current_Instruction.getBit(27).getValue())//POP (not supposed to be implemented)
			{
				Operation= OPERATION.POP;

				new_word.copy(Immediate);
			}
		}
		else if(Current_register==2)
		{
			if(!Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&&!Current_Instruction.getBit(27).getValue())//Rd <- Rd MOP Rs 
			{
				Operation= OPERATION.Math;

				ALU mop= new ALU(Register[(int) RD.getUnsigned()], Register[(int) RS2.getUnsigned()]);
				Bit op[]= new Bit[] {function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)};
				mop.doOperation(op);
				CurrentClockCycle+=mop.num;
				new_word.copy(mop.Result);
			}
			else if(Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())//BRANCH pc <- Rs BOP Rd? pc +imm : pc
			{
				Operation= OPERATION.Branch;

				boolean result=BOP(function, Register[(int) RS2.getUnsigned()],Register[(int) RD.getUnsigned()]);
				if(result== true)
				{
					ALU mop= new ALU(PC, Immediate);
					Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
					mop.doOperation(op);
					new_word.copy(mop.Result);
				}
				else {
					new_word.copy(PC);
				}
			}
			else if(!Current_Instruction.getBit(29).getValue()&&Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())//CALL  pc <- Rs BOP Rd? push pc; pc + imm : pc
			{
				Operation= OPERATION.Call;

				boolean result=BOP(function, Register[(int) RS2.getUnsigned()],Register[(int) RD.getUnsigned()]);
				if(result== true)
				{
					push(PC);
					ALU mop= new ALU(PC, Immediate);
					Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
					mop.doOperation(op);
					new_word.copy(mop.Result);
				}
				else {
					new_word.copy(PC);
				}
			}
			else if(Current_Instruction.getBit(29).getValue()&&Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())//PUSH mem[--sp] <- Rd MOP Rs
			{
				Operation= OPERATION.Push;

				ALU mop= new ALU(Register[(int) RD.getUnsigned()], Register[(int) RS2.getUnsigned()]);
				Bit op[]= new Bit[] {function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)};
				mop.doOperation(op);
				CurrentClockCycle+=mop.num;

				push(mop.Result);
			}
			else if(!Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& Current_Instruction.getBit(27).getValue())//LOAD Rd <- mem[Rs + imm]
			{
				Operation= OPERATION.Load;

				ALU mop= new ALU(Register[(int) RS2.getUnsigned()], Immediate);
				Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
				mop.doOperation(op);
				new_word.copy(mm.read(mop.Result));
				CurrentClockCycle+=300;
			}
			else if(Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& Current_Instruction.getBit(27).getValue())//STORE mem[Rd + imm] <- Rs
			{
				ALU mop= new ALU(Immediate, Register[(int) RD.getUnsigned()]);
				Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
				mop.doOperation(op);
				mm.write(mop.Result, Register[(int) RS2.getUnsigned()]);
				CurrentClockCycle+=300;

				System.out.println("Stored: "+ mm.read(mop.Result).getSigned()+" AT "+ mop.Result.getSigned() );
			}
			else if(!Current_Instruction.getBit(29).getValue()&&Current_Instruction.getBit(28).getValue()&& Current_Instruction.getBit(27).getValue())//pop PEEK: Rd <- mem[sp –(Rs + imm)]
			{
				Operation= OPERATION.POP;

				ALU mop= new ALU(Register[(int) RS2.getUnsigned()], Immediate);
				Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
				mop.doOperation(op);
				ALU mop1= new ALU(SP,mop.Result);
				Bit op1[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(true)};
				mop1.doOperation(op1);
				new_word.copy(mm.read(mop.Result));
				CurrentClockCycle+=300;

			}
		}
		else if(Current_register==3)
		{
			if(!Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&&!Current_Instruction.getBit(27).getValue()) //Rd <- Rs1 MOP Rs2
			{
				Operation= OPERATION.Math;

				ALU mop= new ALU(Register[(int) RS1.getUnsigned()], Register[(int) RS2.getUnsigned()]);
				Bit op[]= new Bit[] {function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)};
				mop.doOperation(op);
				CurrentClockCycle+=mop.num;

				new_word.copy(mop.Result);
			}
			else if(Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())//BRANCH pc <- Rs1 BOP Rs2 ? pc +imm : pc
			{
				Operation= OPERATION.Branch;

				boolean result=BOP(function, Register[(int) RS1.getUnsigned()],Register[(int) RS2.getUnsigned()]);
				if(result== true)
				{
					ALU mop= new ALU(PC,Immediate);
					Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
					mop.doOperation(op);
					new_word.copy(mop.Result);
				}
				else {
					new_word.copy(PC);
				}
			}
			else if(!Current_Instruction.getBit(29).getValue()&&Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())//CALL pc <- Rs1 BOP Rs2 ? push pc; Rd + imm : pc
			{
				Operation= OPERATION.Call;

				boolean result=BOP(function, Register[(int) RS1.getUnsigned()],Register[(int) RS2.getUnsigned()]);
				if(result== true)
				{
					push(PC);
					ALU mop= new ALU(Register[(int) RD.getUnsigned()], Immediate);
					Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
					mop.doOperation(op);
					new_word.copy(mop.Result);
				}
				else {
					new_word.copy(PC);
				}
			}
			else if(Current_Instruction.getBit(29).getValue()&&Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())//PUSH mem[--sp] <- Rs1 MOP Rs2
			{
				Operation= OPERATION.Push;

				ALU mop= new ALU(Register[(int) RS1.getUnsigned()], Register[(int) RS2.getUnsigned()]);
				Bit op[]= new Bit[] {function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)};

				mop.doOperation(op);
				CurrentClockCycle+=mop.num;

				push(mop.Result);
			}
			else if(!Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& Current_Instruction.getBit(27).getValue())//LOAD Rd <- mem [Rs1+ Rs2] 
			{
				Operation= OPERATION.Load;

				ALU mop= new ALU(Register[(int) RS1.getUnsigned()], Register[(int) RS2.getUnsigned()]);
				Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
				mop.doOperation(op);
				new_word.copy(mm.read(mop.Result));
				CurrentClockCycle+=300;

			}
			else if(Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& Current_Instruction.getBit(27).getValue())//STORE  Mem[Rd + Rs1] <- Rs2
			{
				ALU mop= new ALU(Register[(int) RS1.getUnsigned()], Register[(int) RD.getUnsigned()]);
				Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
				mop.doOperation(op);
				mm.write(mop.Result, Register[(int) RS2.getUnsigned()]);
				CurrentClockCycle+=300;

				System.out.println("Stored: "+ mm.read(mop.Result).getSigned()+ " AT "+  mop.Result.getSigned());
			}
			else if(!Current_Instruction.getBit(29).getValue()&&Current_Instruction.getBit(28).getValue()&& Current_Instruction.getBit(27).getValue())//pop  PEEK: Rd <- mem [sp –(Rs1+ Rs2)
			{
				Operation= OPERATION.POP;

				ALU mop= new ALU(Register[(int) RS1.getUnsigned()], Register[(int) RS2.getUnsigned()]);
				Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
				mop.doOperation(op);
				ALU mop1= new ALU(SP,mop.Result);
				Bit op1[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(true)};
				mop1.doOperation(op1);
				new_word.copy(mm.read(mop.Result));
				CurrentClockCycle+=300;

			}

		}
		else 
		{
			if(!Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&&!Current_Instruction.getBit(27).getValue())//COPY: Rd <- imm
			{
				Operation= OPERATION.Math;
				new_word.copy(Immediate);
			}
			else if(Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())//BRANCH JUMP: pc <- pc +imm
			{
				Operation= OPERATION.Branch;

				ALU mop= new ALU(Register[(int) RD.getUnsigned()], Immediate);
				Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
				mop.doOperation(op);
				new_word.copy(mop.Result);
			}
			else if(!Current_Instruction.getBit(29).getValue()&&Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())//CALL push pc; pc <- Rd +imm
			{
				Operation= OPERATION.Call;

				push(PC);
				ALU mop= new ALU(Register[(int) RD.getUnsigned()], Immediate);
				Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
				mop.doOperation(op);
				new_word.copy(mop.Result);
			}
			else if(Current_Instruction.getBit(29).getValue()&&Current_Instruction.getBit(28).getValue()&& !Current_Instruction.getBit(27).getValue())//PUSH mem[--sp] <- Rd MOP imm
			{
				Operation= OPERATION.Push;

				ALU mop= new ALU(Register[(int) RD.getUnsigned()], Immediate);
				Bit op[]= new Bit[] {function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)};
				mop.doOperation(op);
				CurrentClockCycle+=mop.num;

				push(mop.Result);
			}
			else if(!Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& Current_Instruction.getBit(27).getValue())//LOAD Rd <- mem [Rd +imm]
			{
				Operation= OPERATION.Load;

				ALU mop= new ALU(Register[(int) RD.getUnsigned()], Immediate);
				Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
				mop.doOperation(op);
				new_word.copy(mm.read(mop.Result));
				CurrentClockCycle+=300;

			}
			else if(Current_Instruction.getBit(29).getValue()&&!Current_Instruction.getBit(28).getValue()&& Current_Instruction.getBit(27).getValue())//STORE Mem[Rd] <- imm 
			{
				Operation= OPERATION.Store;

				mm.write(Register[(int) RD.getUnsigned()], Immediate);
				CurrentClockCycle+=300;

				System.out.println("Stored: "+ mm.read(Register[(int) RD.getUnsigned()]).getSigned()+ " At "+Register[(int) RD.getUnsigned()].getUnsigned());
			}
			else if(!Current_Instruction.getBit(29).getValue()&&Current_Instruction.getBit(28).getValue()&& Current_Instruction.getBit(27).getValue())//LOAD POP: Rd <- mem[sp++]
			{
				Operation= OPERATION.POP;
				new_word.copy(pop());
			}
		}
	}
	/**
	 * Create decode(): Examine the last 2 bits of the current instruction (which came from fetch())
	 * And then find the R0,r1,r2 or r3 to use
	 */
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
	 * The Push method defined as “decrement SP then write to that address”. 
	 * @param value The word to pe put on to the stack
	 */
	void push(Word value) 
	{
		// Write the value to memory at the stack pointer address
		SP.decrement();
		mm.write(SP, value);
		CurrentClockCycle+=300;

		System.out.println("PUSHED "+value.getSigned()+ " to address "+SP.getSigned());
	}
	/**
	 * The pop method to get something out of the stack
	 * @return The popped word
	 */
	Word pop() 
	{
		if(SP.getUnsigned()<=1024)
		{
			// Read the value from memory at the stack pointer address
			Word poppedValue = mm.read(SP);
			CurrentClockCycle+=300;

			// Increment the stack pointer
			SP.increment();

			// Return the popped value
			return poppedValue;
		}
		else
			return null;
	}
	boolean BOP(Word function,Word R1, Word R2)
	{
		ALU mop= new ALU(R1, R2);
		Bit op[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(true)};
		mop.doOperation(op);
		if(!function.getBit(28).getValue()&& !function.getBit(29).getValue()&& !function.getBit(30).getValue()&&!function.getBit(31).getValue())
		{
			if (mop.Result.getUnsigned()==0)
			{

				return true;
			}
			else
			{
				return false;
			}
		}
		else if(!function.getBit(28).getValue()&& !function.getBit(29).getValue()&& !function.getBit(30).getValue()&& function.getBit(31).getValue())
		{
			if (mop.Result.getUnsigned()!=0)
			{

				return true;
			}
			else
			{
				return false;
			}
		}
		else if(!function.getBit(28).getValue()&&!function.getBit(29).getValue()&& function.getBit(30).getValue()&& !function.getBit(31).getValue())
		{
			if (mop.Result.getUnsigned()==0)
			{

				return false;
			}
			if (mop.Result.getBit(0).getValue()== true)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(!function.getBit(28).getValue()&& !function.getBit(29).getValue()&& function.getBit(30).getValue()&& function.getBit(31).getValue())
		{
			if (mop.Result.getBit(0).getValue()== false)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(!function.getBit(28).getValue()&& function.getBit(29).getValue()&& !function.getBit(30).getValue()&&!function.getBit(31).getValue())
		{
			if (mop.Result.getUnsigned()==0)
			{
				return false;
			}
			if (mop.Result.getBit(0).getValue()== false)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(!function.getBit(28).getValue()&& function.getBit(29).getValue()&& !function.getBit(30).getValue()&& function.getBit(31).getValue())
		{
			if (mop.Result.getBit(0).getValue()== true)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	/**
	 * Get the next instruction from memory (based on the PC). 
	 */
	/**
	 * We facth the current instruction from the main memory and then increase PC so that we can get to the next instruction
	 */
	private void Fetch() {
		// TODO Auto-generated method stub
		Current_Instruction.set(cache.read(PC).getSigned());
		//CurrentClockCycle+=300;


		CurrentClockCycle+=cache.clockCycles; 
		if(L2.calledmem) 
		{ 
			L2.calledmem=false;
			CurrentClockCycle+=L2.clockCycles; 
		}


		PC.increment();
	}
}