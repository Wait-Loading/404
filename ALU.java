
public class ALU 
{
	Word op1;//The 32bits Word on which we want to do a operation
	Word op2;//An another 32bits Word on which we want to do a operation
	Word Result;//The result of the operation we want to store in here
	/**
	 * The constructor for ALU 
	 * @param op1 : Input to Set the op1 of the ALU
	 * @param op2 : Input to Set the op2 of the ALU
	 */
	ALU(Word op1,Word op2)
	{
		this.op1=op1;
		this.op2=op2;
		Result= new Word();

	}
	/**
	 * The method that need to be called to set the operation of the ALU 
	 * This method will look at the array of bits (there will be 4) and determine the operation:
     *1000 – and
     *1001 – or
     *1010 – xor 
     *1011 – not (not “op1”; ignore op2)
     *1100 – left shift (“op1” is the value to shift, “op2” is the amount to shift; ignore all but the lowest 5 bits)
     *1101 – right shift (“op1” is the value to shift, “op2” is the amount to shift; ignore all but the lowest 5 bits)
     *1110 – add
     *1111 – subtract
     *0111 - multiply
	 * @param operation an array of 4 Bit that has combinations for the above scheme
	 */
public void doOperation(Bit [] operation)
{
	if (!operation[3].getValue())//if the last bit of operation is 0 then we want to only go for and, left shift,Xor,add
	{
		if (!operation[2].getValue())//here we only want to look at and , left operation
		{
			
			if(!operation[1].getValue()&&operation[0].getValue() )
			{
				//and
				Result.copy(op1.and(op2));
			}
			else if(operation[1].getValue()&&operation[0].getValue())
			{
				//left shift
				Word copy_o2 = new Word();
				copy_o2.copy(op2);
				for(int i=0;i<=26;i++)//A loop that we run to ignore the 27 bits and only look 5 bits, we can do this as bit shift do not work on negative numbers
				{
					copy_o2.setBit(i, new Bit(false));
				}
                Result.copy(op1.leftShift(copy_o2.getSigned()));
			}
		}
		else
		{
			if(!operation[1].getValue()&&operation[0].getValue() )
			{
				//xor
				Result.copy(op1.Xor(op2));
			}
			else if(operation[1].getValue()&&operation[0].getValue())
			{
		    //add
				Result.copy(add2(op1,op2));
			}
		}
	}
	else//if the last bit of operation is 1 then we want to only go for Xor, left
	{
		if(!operation[2].getValue())
		{
			//or
			if(!operation[1].getValue()&&operation[0].getValue() )
			{
				//or
				Result.copy(op1.or(op2));
			}
			else if(operation[1].getValue()&&operation[0].getValue())
			{
		    //right
				Word copy_o2 = new Word();
				copy_o2.copy(op2);
				for(int i=0;i<=26;i++)
				{
					copy_o2.setBit(i, new Bit(false));
				}
                Result.copy(op1.rightShift(copy_o2.getSigned()));
			}
		}
		else
		{
			if(!operation[1].getValue()&&operation[0].getValue() )
			{
				//not
				Result.copy( op1.not());
			}
			else if(operation[1].getValue()&&operation[0].getValue())
			{
		    //sub
				Result.copy( Sub(op1,op2));
			}
			else if(operation[1].getValue()&&!operation[0].getValue())
			{
				//multiply
				Result.copy( multiply(op1,op2));
			}
		}
		
	}
}
/**
 * A method to add 2 bits
 * @param bit1:  First bit
 * @param bit2:  Second Bit
 * @param carryIn: The bit that is to be carried in
 * @return an array of 2 bits bit carry out and sum
 */
private Bit[] add2_bits(Bit bit1, Bit bit2, Bit carryIn) 
{
    Bit sum = bit2.Xor(bit1).Xor(carryIn);//S = X XOR Y XOR Cin
    Bit carryOut = (bit1.and(bit2)).or((bit1.Xor(bit2)).and(carryIn));//Cout = X AND Y OR (( X XOR Y) AND Cin)
    return new Bit[]{sum, carryOut};
}
/**
 * Takes in 2 words and add them
 * @param op1 the first word
 * @param op2 The second word to add
 * @return the word with addiction of the two
 */
private Word add2(Word op1, Word op2)
{
	Word result= new Word();
	Bit carry = new Bit(false);//setting the default carry to false
    for (int i =31 ; i >=0; i--) 
    {
        Bit[] sum = add2_bits(op1.getBit(i), op2.getBit(i), carry);//adding 2 bit one by one and dealing with the carry 
        result.setBit(i, sum[0]);
        carry = sum[1];//setting the new carry that came as a sum of the other 2 bits
    }
    return result;
}
/**
 * The Method that subtract 2 words
 * @param op1
 * @param op2
 * @return Subtracted word
 */
private Word Sub(Word op1, Word op2)
{
	Word one= new Word();// making a new word with value of 1 so we can do -op2 = not (op2) + 1
	one.set(1);
	Word copy;
	copy=op2.not();
    return add2(op1, add2(copy,one));//op1 + -op2. 
}
/**
 * The method that adds 4 words
 * @param word1 
 * @param word2
 * @param word3
 * @param word4
 * @return A word with the addition of four words
 */
public Word add4(Word word1, Word word2, Word word3, Word word4) 
{
	Word sum = new Word();//The Word that does the sum of the particular bit and resets
	Word carry1 = new Word();//carry1 will hold the most recent carry
	Word carry2 = new Word();//carry2 will hold the value of carry1 after the we are done with first carry1 and we need to use it to make a new carry1 
	Word result= new Word();//this will hold the final result of our sum.
	int count_one=0;//the number of 1's in the certain addition of 6 bits 
	Word sum6[]= new Word[] {word1,word2,word3,word4,carry1,carry2};//An array that can hold all of the words we will need to use during the addition
	int left=0;//The left shift on the carry1 this will handle the carry that we want to do on the sum
	for(int j=31;j>=0;j--)
	{
		sum=word1.Xor(word2).Xor(word3).Xor(word4).Xor(carry1).Xor(carry2);
		result.setBit(j, sum.getBit(j));//it will take the right value that we found by the sum of all six bits at index j so even when the sum resets we will be good
		for (int i=0;i<6;i++)
		{
			if(sum6[i].getBit(j).getValue())
			{
				count_one++;
			}
		}
		carry2.copy(carry1);
		sum6[5]=carry2;//as the carry2 has changed we change it in the sum6 array 

		carry1.set(count_one/2);//we set the new carry1 or the most recent carry one;
		carry1=carry1.leftShift(++left);//we shift the carry by ++left so it increments as we want it to be 
		sum6[4]=carry1;//as the carry1 has changed we change it in the sum6 array
		count_one=0;  //reset the count
	}
	return result;
}
/**
 * Method that multiplies 2 words using the three rounds
 * use 8 4-way adders for round 1. 
 * If they each add 4 numbers, that will reduce the number of adds down to 8. 
 * For round 2, we will use 2 more 4-way adders. That takes us down to 2 adds.
 * For round 3, we will use a 2-way adder which will give us the final result.
 * @param op1 
 * @param op2
 * @return The multiplied word
 */
public Word multiply(Word op1, Word op2)
{
    Word[] multiplied = new Word[32];
    int left=0;
    for (int i = 31; i >= 0; i--) 
    {
        if (op2.getBit(i).getValue()) 
        {
            multiplied[i] = op1.leftShift(left);
        } 
        else
        {
            multiplied[i] = new Word();
        }
        left++;
    }
    Word[] round1_sums = new Word[8];
    int word=0;
    for (int i = 0; i < 8; i++) 
    {
        round1_sums[i] = add4(multiplied[word++], multiplied[word++], multiplied[word++], multiplied[word++]);
    }
    word=0;
    Word[] round2_sums = new Word[2];
    for (int i = 0; i < 2; i++) 
    {
        round2_sums[i] = add4(round1_sums[word++], round1_sums[word++], round1_sums[word++], round1_sums[word++]);
    }
    Word result = add2(round2_sums[0], round2_sums[1]);
    return result;
}
}