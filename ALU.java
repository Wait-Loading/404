
public class ALU 
{
	Word op1;
	Word op2;
	Word Result;
	ALU(Word op1,Word op2)
	{
		this.op1=op1;
		this.op2=op2;
		Result= new Word();

	}
public void doOperation(Bit [] operation)
{
	if (!operation[3].getValue())
	{
		if (!operation[2].getValue())
		{
			
			if(!operation[1].getValue()&&operation[0].getValue() )
			{
				//and
				Result=op1.and(op2);
			}
			else if(operation[1].getValue()&&operation[0].getValue())
			{
				//left shift
				Word copy_o2 = new Word();
				copy_o2.copy(op2);
				for(int i=0;i<=26;i++)
				{
					copy_o2.setBit(i, new Bit(false));
				}
                Result=op1.leftShift(copy_o2.getSigned());
			}
		}
		else
		{
			
			if(!operation[1].getValue()&&operation[0].getValue() )
			{
				//xor
				Result=op1.Xor(op2);
			}
			else if(operation[1].getValue()&&operation[0].getValue())
			{
		    //add
				Result=add2(op1,op2);
			}
		}
	}
	else
	{
		if(!operation[2].getValue())
		{
			//or
			if(!operation[1].getValue()&&operation[0].getValue() )
			{
				//or
				Result=op1.or(op2);
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
                Result=op1.rightShift(copy_o2.getSigned());
			}
		}
		else
		{
			if(!operation[1].getValue()&&operation[0].getValue() )
			{
				//not
				Result= op1.not();
			}
			else if(operation[1].getValue()&&operation[0].getValue())
			{
		    //sub
				Result= Sub(op1,op2);
			}
			else if(operation[1].getValue()&&!operation[0].getValue())
			{
				//multiply
				Result= multiply(op1,op2);
			}
		}
		
	}
}
private Bit[] add2_bits(Bit bit1, Bit bit2, Bit carryIn) {
	
    Bit sum = bit1.Xor(bit2).Xor(carryIn);
    Bit carryOut = (bit1.and(bit2)).or((bit1.Xor(bit2)).and(carryIn));
    return new Bit[]{sum, carryOut};
}
private Word add2(Word op1, Word op2) {
	Word result= new Word();
	Bit carry = new Bit(false);
    for (int i =31 ; i >=0; i--) {
        Bit[] sum = add2_bits(op1.getBit(i), op2.getBit(i), carry);
        result.setBit(i, sum[0]);
        carry = sum[1];
    }
    return result;
}
private Word Sub(Word op1, Word op2) {
	Word one= new Word();
	one.set(1);
	Word copy;
	copy=op2.not();
    return add2(op1, add2(copy,one));
}



public Word add4(Word word1, Word word2, Word word3, Word word4) {
    Word sum = new Word();
    Word carry1 = new Word();
    Word carry2 = new Word();
    Word result= new Word();
int count_one=0;
   Word sum4[]= new Word[] {word1,word2,word3,word4,carry1,carry2};
   int left=0;
   
for(int j=31;j>=0;j--)
{
	sum=word1.Xor(word2).Xor(word3).Xor(word4).Xor(carry1).Xor(carry2);
	result.setBit(j, sum.getBit(j));
	sum4[5]=carry2;
	
   for (int i=0;i<6;i++)
   {
	   if(sum4[i].getBit(j).getValue())
	   {
		   count_one++;
	   }
	  
   }
	carry2.copy(carry1);

   carry1.set(count_one/2);
   carry1=carry1.leftShift(++left);
	sum4[4]=carry1;
   count_one=0;
  // carry1.copy(carry2);

   
}
    return result;
}




//Method to multiply two numbers using 2-way and 4-way adders
public Word multiply(Word op1, Word op2) {
    Word[] multiplied = new Word[32];
    int left=0;
    for (int i = 31; i >= 0; i--) {
        if (op2.getBit(i).getValue()) {
            multiplied[i] = op1.leftShift(left);
        } else {
            multiplied[i] = new Word();
        }
        left++;
    }

    Word[] round1_sums = new Word[8];
    int word=0;
    for (int i = 0; i < 8; i++) {
        round1_sums[i] = add4(multiplied[word++], multiplied[word++], multiplied[word++], multiplied[word++]);
    }
    word=0;
    Word[] round2_sums = new Word[2];
    for (int i = 0; i < 2; i++) {
        round2_sums[i] = add4(round1_sums[word++], round1_sums[word++], round1_sums[word++], round1_sums[word++]);
    }

    Word result = add2(round2_sums[0], round2_sums[1]);
  
    return result;
}

}