import java.io.IOException;
import java.util.*;
/**
 * The assembler class that needs the instruction in the following format 
 * 
 * Opcode immediate_value function RS1 RS2 RD 
 * ";" Can be used as separator for next line
 * LINE 17 to 400 is the PARSER CLASS
 * LINE 401 to 720 is the LEXER CLASS
 * 
 */
class Assembler
{
	static Lexer lexerobj;//The lexer Object That we use to read 
	static LinkedList<Token> tokens;//The tokens produced by the lexer
	Parser parser;//The parser that gives meaning to the lexer
	class Parser
	{
		private TokenManager Manager;  //Making object of Token Manager class to handle the stream of tokens.			LinkedList<String> instructions= new LinkedList<String>();
		LinkedList<String> instructions= new LinkedList<String>();
		/**
		 * 
		 * @param tokens: LinkedList of tokens as an input.
		 */
		public Parser(LinkedList<Token> tokens) //Constructor for Parser
		{
			Manager = new TokenManager(tokens);
		}
		/**
		 * The Accept separator is made
		 * @return if the separator is removed or not
		 */
		boolean AcceptSeperators()
		{
			if(Manager.MoreTokens())
			{
				if((Manager.Peek(0).get().getType())==Token.TokenType.SEPERATOR)//Peeking for a seperator
				{
					while(Manager.MatchAndRemove(Token.TokenType.SEPERATOR).isPresent()&&Manager.MoreTokens())//Matching and removing SEPERATOR as we find them only if there are more tokens
					{

					}
					return true;
				}
				else
				{
					return false;
				}
			}
			else 
				return false;
		}
		/**
		 * The Parse object
		 */
		public void Parse()
		{
			while(Manager.MoreTokens())
			{
				boolean Action = ParseStatementS();
				}
		}
		/**
		 * The Method that convert the number to a string of binary
		 * @param number The number to be converted
		 * @return The binary of the number in string
		 */
		public String toBinaryString(int number) {
	        if (number == 0)
	        {
	            return "0";
	        }

	        StringBuilder SB = new StringBuilder();
	        while (number > 0) 
	        {
	            SB.insert(0, number % 2);
	            number /= 2;
	        }

	        return SB.toString();
	    }
		/**
		 * The Methode to  parse statements.
		 * @return True or false on based of the statement
		 */
		private boolean ParseStatementS() 
		{
			while(Manager.MoreTokens())//While The assembler have more tokens we loop to make the linked list of the instructions.
			{
				int register=0;//The register number is set to 0 at the begining 
				Iterator<Token> iterator = Manager.iterator();
				while (iterator.hasNext()) //A loop to count the register we want to use on the processor
				{
					
					Token token = iterator.next();
					Token.TokenType TT= token.getType();
					if(TT==Token.TokenType.SEPERATOR)
					{
						break;
					}
					if(TT==Token.TokenType.Register)
					{
						register++;
					}
					
				}
				String instruction="";
				instruction = parseOperation();//We parse the operation and get the OP code
				// Switching on the register to determine what token we need to work on.
				if(register<4)
				{
					switch(register)
					{
					case 0://The current register is 0 on the processor so we will look for Immediate  only.
						instruction=instruction+"00";
						if(Manager.Peek(0).get().getType()==Token.TokenType.NUMBERS)
						{
							int i=Integer.parseInt( Manager.MatchAndRemove(Token.TokenType.NUMBERS).get().getValue());
							instruction=toBinaryString(i)+instruction;
						}
						for(int i=instruction.length();i<32;i++)//Making sure if to complete the string instruction
						{
							instruction= "0"+instruction;
						}
						
						break;
					case 1://The current register is 1 on the processor so we will look for Immediate  , function and RD .
						instruction=instruction+"01";
						
					  String st=	parseFunction();
					  if(Manager.Peek(0).get().getType()==Token.TokenType.NUMBERS)
						{
							int i=Integer.parseInt( Manager.MatchAndRemove(Token.TokenType.NUMBERS).get().getValue());
							st=toBinaryString(i)+st;
						}
						Token.TokenType TT = Manager.Peek(0).get().getType();
						
						if(TT==Token.TokenType.Register)
						{
							int r= Integer.parseInt(Manager.MatchAndRemove(Token.TokenType.Register).get().getValue().substring(1));
							String rs= toBinaryString(r);
							for(int i=rs.length();i<5;i++)
							{
								rs="0"+rs;
							}
							st=st+rs;
						}
						instruction=st+instruction;
						
						for(int i=instruction.length();i<32;i++)//Making sure if to complete the string instruction
						{
							instruction= "0"+instruction;
						}

						break;
					case 2://The current register is 0 on the processor so we will look for Immediate ,RD,RS and function only.
						instruction=instruction+"10";
						String St=	"";
						if(Manager.Peek(0).get().getType()==Token.TokenType.NUMBERS)
						{
							int i=Integer.parseInt( Manager.MatchAndRemove(Token.TokenType.NUMBERS).get().getValue());
							St=toBinaryString(i)+St;
						}
						St= St+parseFunction();
						if(Manager.Peek(0).get().getType()==Token.TokenType.Register)
						{
							int r= Integer.parseInt(Manager.MatchAndRemove(Token.TokenType.Register).get().getValue().substring(1));
							String rs= toBinaryString(r);
							for(int i=rs.length();i<5;i++)
							{
								rs="0"+rs;
							}
							St=St.substring(0,St.length()-4)+rs+St.substring(St.length()-4);
						}
						Token.TokenType tt = Manager.Peek(0).get().getType();
						if(tt==Token.TokenType.Register)
						{
							int r= Integer.parseInt(Manager.MatchAndRemove(Token.TokenType.Register).get().getValue().substring(1));
							String rs= toBinaryString(r);
							for(int i=rs.length();i<5;i++)
							{
								rs="0"+rs;
							}
							instruction=rs+instruction;
						}
						instruction=St+instruction;
						for(int i=instruction.length();i<32;i++)//Making sure if to complete the string instruction
						{
							instruction= "0"+instruction;
						}
						break;
					case 3://The current register is 0 on the processor so we will look for Immediate,RS1,RS2,RD and function.
						instruction=instruction+"11";
						String st3=	"";
						if(Manager.Peek(0).get().getType()==Token.TokenType.NUMBERS)
						{
							int i=Integer.parseInt( Manager.MatchAndRemove(Token.TokenType.NUMBERS).get().getValue());
							st3=toBinaryString(i)+st3;
						}
						 st3=	st3+parseFunction();
						if(Manager.Peek(0).get().getType()==Token.TokenType.Register)
						{
							int r= Integer.parseInt(Manager.MatchAndRemove(Token.TokenType.Register).get().getValue().substring(1));
							String rs= toBinaryString(r);
							for(int i=rs.length();i<5;i++)
							{
								rs="0"+rs;
							}
							st3=st3.substring(0,st3.length()-4)+rs+st3.substring(st3.length()-4);
						}
						if(Manager.Peek(0).get().getType()==Token.TokenType.Register)
						{
							int r= Integer.parseInt(Manager.MatchAndRemove(Token.TokenType.Register).get().getValue().substring(1));
							String rs= toBinaryString(r);
							for(int i=rs.length();i<5;i++)
							{
								rs="0"+rs;
							}
							st3=st3.substring(0,st3.length()-4)+rs+st3.substring(st3.length()-4);
						}
						Token.TokenType TType = Manager.Peek(0).get().getType();
						if(TType==Token.TokenType.Register)
						{
							int r= Integer.parseInt(Manager.MatchAndRemove(Token.TokenType.Register).get().getValue().substring(1));
							String rs= toBinaryString(r);
							for(int i=rs.length();i<5;i++)
							{
								rs="0"+rs;
							}
							instruction=rs+instruction;
						}
						instruction=st3+instruction;
						for(int i=instruction.length();i<32;i++)//Making sure if to complete the string
						{
							instruction= "0"+instruction;
						}
						break;
					default:
						return false;
					}
					
				}
				else
				{
					return false;
				}
				instructions.add(instruction);
                Manager.MatchAndRemove(Token.TokenType.SEPERATOR);
			}
			return false;

		}
		/**
		 * The parseFUnction function that returns the string of function on the token
		 * @return the string of function on the token
		 */
		String parseFunction()
		{
			if(Manager.MatchAndRemove(Token.TokenType.and).isPresent())
			{
				return "1000";
			}
			else if(Manager.MatchAndRemove(Token.TokenType.or).isPresent())
			{
				return "1001";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.xor).isPresent())
			{
				return "1010";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.not).isPresent())
			{
				return "1011";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.left).isPresent())
			{
				return "1100";
			}
			else if(Manager.MatchAndRemove(Token.TokenType.right).isPresent())
			{
				return "1101";
			}
			else if(Manager.MatchAndRemove(Token.TokenType.add).isPresent())
			{
				return "1110";
			}
			else if(Manager.MatchAndRemove(Token.TokenType.subtract).isPresent())
			{
				return "1111";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.multiply).isPresent())
			{
				return "0111";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.equal).isPresent())
			{
				return "0000";
			}
			else if(Manager.MatchAndRemove(Token.TokenType.unequal).isPresent())
			{
				return "0001";
			}
			else if(Manager.MatchAndRemove(Token.TokenType.less).isPresent())
			{
				return "0010";
			}
			else if(Manager.MatchAndRemove(Token.TokenType.greaterOrEqual).isPresent())
			{
				return "0011";
			}
			else if(Manager.MatchAndRemove(Token.TokenType.greater).isPresent())
			{
				return "0100";
			}
			else if(Manager.MatchAndRemove(Token.TokenType.lessOrEqual).isPresent())
			{
				return "0101";
			}
			else
			{
				return "0000";
			}
		}
		/**
		 * The function to Parse the operation and get the OP COde
		 * @return
		 */
		private String parseOperation() {
			// TODO Auto-generated method stub
			if(Manager.MatchAndRemove(Token.TokenType.math).isPresent())
			{
				return "000";
			}
			else if(Manager.MatchAndRemove(Token.TokenType.branch).isPresent())
			{
				return "001";
			}
			else if(Manager.MatchAndRemove(Token.TokenType.halt).isPresent())
			{
				return "000";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.copy).isPresent())
			{
				return "000";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.jump).isPresent())
			{			    
				return "001";
			}
			else if(Manager.MatchAndRemove(Token.TokenType.push).isPresent())
			{
				return "011";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.pop).isPresent())
			{
				return "110";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.load).isPresent())
			{
				return "100";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.store).isPresent())
			{
				return "101";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.Return).isPresent())
			{
				return "100";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.peek).isPresent())
			{
				return "000";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.pop).isPresent())
			{
				return "110";

			}
			else if(Manager.MatchAndRemove(Token.TokenType.call).isPresent())
			{
				return "010";

			}
			return null;
		}
	}
	public class Lexer 
	{
		private static READ_handler handler;// READ_handler object to call class READ_handler when needed.
		private int CurrentLINE;// INT variable to store the position of the current line.
		int CurrentPOS;//INT variable to store the current position in the line.
		ArrayList<String> Comments = new ArrayList<String>();
		HashMap<String,Token.TokenType> map=new HashMap<>();//Creating a hashmap of key words
		HashMap<String,Token.TokenType> twocharsymbol=new HashMap<>();//Hashmap of 2 charACTER SYMBOLS
		HashMap<String,Token.TokenType> onecharsymbol=new HashMap<>();//Hashmap of 1 charACTER SYMBOLS
		/**
		 * Constructor 
		 * @param input takes the file name as an input.
		 */
		Lexer(String input)
		{
			handler =new READ_handler(input);//Creating a read Handler.
			CurrentPOS=1;
			CurrentLINE=1;
			//adding keys to hash map
			map.put("math", Token.TokenType.math);
			map.put("add", Token.TokenType.add);
			map.put("subtract", Token.TokenType.subtract);
			map.put("multiply", Token.TokenType.multiply);
			map.put("and", Token.TokenType.and);
			map.put("or", Token.TokenType.or);
			map.put("not", Token.TokenType.not);
			map.put("xor", Token.TokenType.xor);
			map.put("copy", Token.TokenType.copy);
			map.put("halt", Token.TokenType.halt);
			map.put("branch", Token.TokenType.branch);
			map.put("jump", Token.TokenType.jump);
			map.put("call", Token.TokenType.call);
			map.put("push", Token.TokenType.push);
			map.put("load", Token.TokenType.load);
			map.put("return", Token.TokenType.Return);
			map.put("store", Token.TokenType.store);
			map.put("peek", Token.TokenType.peek);
			map.put("pop", Token.TokenType.pop);
			map.put("interrupt", Token.TokenType.interrupt);
			map.put("equal", Token.TokenType.equal);
			map.put("unequal", Token.TokenType.unequal);
			map.put("greater", Token.TokenType.greater);
			map.put("less", Token.TokenType.less);
			map.put("greaterOrEqual", Token.TokenType.greaterOrEqual);
			map.put("lessOrEqual", Token.TokenType.lessOrEqual);
			map.put("shift", Token.TokenType.shift);
			map.put("left", Token.TokenType.left);
			map.put("right", Token.TokenType.right);
			String reg[]= new String[32];
			for(int i=0;i<=31;i++)
			{
				reg[i]= "R";
				reg[i]= reg[i]+i;
			}
			for(int i = 0; i <= 31; i++) {
				switch(i) {
				case 0:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 1:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 2:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 3:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 4:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 5:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 6:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 7:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 8:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 9:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 10:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 11:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 12:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 13:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 14:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 15:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 16:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 17:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 18:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 19:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 20:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 21:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 22:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 23:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 24:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 25:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 26:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 27:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 28:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 29:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 30:
					map.put(reg[i], Token.TokenType.Register);
					break;
				case 31:
					map.put(reg[i], Token.TokenType.Register);
					break;
				default:
					// Handle any default case if needed
					break;
				}
			}


		}
		/**
		 * 	  this is the main which will break the data from READ handler into a linked list of tokens.  

		 * @return The linked list of the tokens
		 * @throws IOException 
		 */
		List<Token> Lex() throws IOException
		{
			char ch;//A variable to store the characters we will peek at
			List<Token> tokens = new LinkedList<>();//Linked list of tokens
			//while loop that will run till the index has reached the file end.
			while(handler.ISDone()==false)
			{

				ch= handler.Peek(0);//Peeking at character
				if(ch=='\r')//If the character is a carriage return (\r), we will ignore it.
				{
					handler.Swallow(1);//moving index without doing anything
				}
				else if(ch==' ')//If the character is a space or tab, we will just move past it (increment position). 
				{
					handler.Swallow(1);//moving index without doing anything
					if(handler.ISDone())
						break;
					CurrentPOS++;//moving the position to the next index.
				}
				else if(ch=='\n')//If the character is a linefeed (\n), we will create a new SEPERATOR token with no value and add it to token list. 
				{
					tokens.add(new Token(Token.TokenType.SEPERATOR,CurrentLINE,CurrentPOS));//
					CurrentPOS=1;//changing the position to 1 for the new line.
					handler.Swallow(1);
					if(handler.ISDone())
						break;
					CurrentLINE++;//updating to the new line
				}
				else if (((int)ch>=65&&(int)ch<=90)||((int)ch>=97&&(int)ch<=122))//If the character is a letter, we need to call ProcessWord and add the result to our list of tokens
				{
					Token token = ProcessWord();//creating a token object
					tokens.add(token);
				}
				else if((Character.isDigit(ch)|| ch == '.'))//If the character is a digit, we need to call ProcessDigit and add the result to our list of tokens.
				{
					Token token = ProcessNumber();//creating a token object
					tokens.add(token); 
				}
				else if(ch=='#')//if the character is a comment in awk.
				{
					String srt="";//A string to store the comment
					while(ch!='\n')
					{
						if(handler.ISDone())
							break;
						ch= handler.Peek(0);
						srt=srt+ch;
						handler.Swallow(1);
					}
					// System.out.println("Comment on line "+CurrentLINE+": "+srt);
					CurrentLINE++;//updating to the new line
				}

				else if(onecharsymbol.containsKey(Character.toString(ch)))
				{
					Token token=ProcessSymbols();
					tokens.add(token);
				}
				else
				{
					System.out.println("EXCEPTION: CHARACTER NOT RECOGNISED AT LINE "+ CurrentLINE +" INDEX: " + CurrentPOS);//Throwing a exception if the character is not recognized
					handler.Swallow(1);
				}

			}
			return tokens;
		} 
		/**
		 * Method to process word and return its token
		 * @return  token of words
		 * @throws IOException 
		 */
		private Token ProcessWord() throws IOException 
		{   			

			int firstindex=CurrentPOS;//Storing the index of first character of each token.
			String value= "";
			char ch=handler.Peek(0);  
			while ((Character.isLetterOrDigit(ch)||ch=='_')&&(handler.ISDone()==false))
			{	 
				value= value+ch; 
				handler.Swallow(1);
				if(handler.ISDone())
					break;
				ch=handler.Peek(0);
				CurrentPOS++;
			}
			if(map.containsKey(value))//checking for keywords
			{
				if(Token.TokenType.Register==map.get(value))
				{
					Token keyword=new Token(map.get(value),CurrentLINE,firstindex,value);
					return keyword;
				}
				Token keyword=new Token(map.get(value),CurrentLINE,firstindex);
				return keyword;
			}
			else
			{
		        throw new IOException();
			}
		}

		/**
		 * Method to process number and return its token
		 * @return number of tokens
		 */
		private Token ProcessNumber() 
		{
			int firstindex=CurrentPOS;
			String value="";
			char ch=handler.Peek(0);
			while ((Character.isDigit(ch) || ch == '.')&&(handler.ISDone()==false))
			{ 
				value= value+ch;
				handler.Swallow(1);
				if(handler.ISDone())
					break;
				ch=handler.Peek(0);
				CurrentPOS++;
			}

			return (new Token(Token.TokenType.NUMBERS,CurrentLINE,firstindex,value));
		}


		/**
		 * Method  to process symbols and give symbols
		 * @return TokenType of symbols
		 */
		public Token ProcessSymbols()
		{
			int firstindex=CurrentPOS;
			String symbol=handler.PeekString(2);
			String ch=handler.PeekString(1);
			if(twocharsymbol.containsKey(symbol))//checking the 2 character symbols first.
			{
				CurrentPOS +=2;
				handler.Swallow(2);
				return (new Token(twocharsymbol.get(symbol),CurrentLINE,firstindex,symbol));
			}
			else if(onecharsymbol.containsKey(ch))//checking the one character symbols
			{
				CurrentPOS++;
				if (ch=="\n"||ch==";")
					CurrentLINE++;
				handler.Swallow(1);
				return (new Token(onecharsymbol.get(ch),CurrentLINE,firstindex,ch));
			}
			return null;
		}
	}
	/**
	 * The assembler constructor that takes in the input of the ASSEMBLER code
	 * @param input The assembler code
	 * @throws IOException
	 */
	public Assembler(String input) throws IOException
	{

		lexerobj = new Lexer(input); //creating lexer object for input.
		tokens = (LinkedList)lexerobj.Lex();
		parser= new Parser(tokens);
		parser.Parse();
	}
}