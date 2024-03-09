import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class test {

	@Test
	void test_bit()
	{
	
        Bit bit = new Bit(true);// Testing constructor and getValue
         assertTrue(bit.getValue());
         bit.toggle();// Testing toggle
        assertFalse(bit.getValue());
         bit.set();// Testing set
         assertTrue(bit.getValue());
         bit.set(false);
         assertFalse(bit.getValue());
         bit.clear();  // Testing clear
      assertFalse(bit.getValue());
       Bit b1 = new Bit(true);
       Bit b2 = new Bit(false);
     Bit And_Result = b1.and(b2);
        assertFalse(And_Result.getValue());// Testing AND operation
      Bit Xor_Result = b1.Xor(b2);
       assertTrue(Xor_Result.getValue()); // Testing XOR operation
        Bit Or_Result = b1.or(b2);
       assertTrue(Or_Result.getValue());// Testing OR operation
        Bit Not_Result = b1.not();
        assertFalse(Not_Result.getValue());// Testing NOT operation
      assertEquals("f", Not_Result.toString());// Testing toString

	}
	/**
	 * The test to check the getunsigned()
	 * We test by setting all value to true and see if we get the max value that we can get
	 */
	@Test
	void test_getunsigned() 
	{
		Word w= new Word();
		for (int i=0;i<32;i++)
		{
			w.setBit(i, new Bit(true));
		}
		long result = w.getUnsigned();
		long num = 4294967295L;//The max value after we set all value as true
		assertEquals(num,result);
		for (int i=0;i<32;i++)
		{
			w.setBit(i, new Bit(false));
		}
		 result = w.getUnsigned();
		 num = 0;//The max value after we set all value as true
		assertEquals(num,result);
		
		
		
		
	}
	/**
	 * This test checks the get signed value method and also set(int value) method
	 */
	@Test
	void test_getSigned()
	{
		Word w= new Word();
		for (int i=0;i<32;i++)
		{
			w.setBit(i, new Bit(true));//setting all value as true so we can make -2147483647
		}
		int result = w.getSigned();
		int num = -1;//The lowest value when we set the least significant value at index 0 as true
		assertEquals(num,result);
		
		w.setBit(0, new Bit(false));//Changing the sign by changing the lest significant value
		result = w.getSigned();
		num = 2147483647;
		assertEquals(num,result);
		
		for (int i=0;i<32;i++)
		{
			w.setBit(i, new Bit(false));//setting all value as true so we can make -2147483647
		}
		result = w.getSigned();
		num = 0;
		assertEquals(num,result);
		
		
		w.set(-4);// We use the set(int value)
		num=-4;
		result = w.getSigned();// we get the signed value of the word 
		assertEquals(num,result);
		
		w.set(4);
		num=4;
		result = w.getSigned();
		assertEquals(num,result);
		
		w.set(12);// We set the value as 12 and check it by using the to string method
		String output=w.toString();
		assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,f,f",output);
	}
	@Test
	void test_gates()
	{
		/*     A B  A and B
         * 31  1 1     1
         * 30  1 0     0
         * 29  0 1     0
         * 28  0 0     0
         */
		Word w= new Word();
        w.setBit(31, new Bit(true));
        w.setBit(30, new Bit(true));
        w.setBit(29, new Bit(false));
        w.setBit(28, new Bit(false));
		Word w2= new Word();
		w2.setBit(31, new Bit(true));
	    w2.setBit(30, new Bit(false));
	    w2.setBit(29, new Bit(true));
	    w2.setBit(28, new Bit(false));
	    
	    Word And_W = w.and(w2);
	    String output=And_W.toString();
	    assertEquals(And_W.getBit(31).getValue(),true);
	    assertEquals(And_W.getBit(30).getValue(),false);
	    assertEquals(And_W.getBit(29).getValue(),false);
	    assertEquals(And_W.getBit(28).getValue(),false);
		assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t",output);
		/*     A B  A or B
         * 31  1 1     1
         * 30  1 0     1
         * 29  0 1     1
         * 28  0 0     0
         */
	    Word Or_W = w.or(w2);
	    output=Or_W.toString();
	    assertEquals(Or_W.getBit(31).getValue(),true);
	    assertEquals(Or_W.getBit(30).getValue(),true);
	    assertEquals(Or_W.getBit(29).getValue(),true);
	    assertEquals(Or_W.getBit(28).getValue(),false);
		assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,t",output);
		/*     A B  A Xor B
         * 31  1 1     0
         * 30  1 0     1
         * 29  0 1     1
         * 28  0 0     0
         */
		 Word XOr_W = w.Xor(w2);
		 output=XOr_W.toString();
		 assertEquals(XOr_W.getBit(31).getValue(),false);
		 assertEquals(XOr_W.getBit(30).getValue(),true);
		 assertEquals(XOr_W.getBit(29).getValue(),true);
		 assertEquals(XOr_W.getBit(28).getValue(),false);
	     assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,f",output);
	        /*     A !A 
	         * 31  1  0     
	         * 30  1  0     
	         * 29  0  1     
	         * 28  0  1  
	         * 27  0  1 
	         *   .....
	         *   ....
	         *   .... and so on   
	         */
	     Word Not_w = w.not();
		 output=Not_w.toString();
	     assertEquals("t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,f,f",output); 
	}	
	@Test
	void test_shifts()
	{
		/*
		 * 7 shifted by one to right is 3
		 * 7 shifted by one to left is 14
		 * 7 shifted by two to right is 1
		 * 7 shifted by two to left is 28
		 */
		Word w = new Word();
        w.set(7);
        Word Right= w.rightShift(1);
	    assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,t",w.toString());
	    assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t",Right.toString()); 
	    assertEquals(3,Right.getSigned());
	    
	    Word Left=w.leftShift(1);
	    assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,t,f",Left.toString()); 
	    assertEquals(14,Left.getSigned());
	    
	    Right= w.rightShift(2);
	    assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t",Right.toString()); 
	    assertEquals(1,Right.getSigned());
	    
	    Left = w.leftShift(2);
	    assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,t,f,f",Left.toString()); 
	    assertEquals(28,Left.getSigned());
	}
	/**
	 * Testing the copy word method
	 */
	@Test
	void TestCopy()
	{
		Word w = new Word();
        w.set(21);
        Word copy_w= new Word ();
        copy_w.copy(w);
        assertEquals(21,copy_w.getSigned());
        assertEquals(w.toString(),copy_w.toString());
        
	}
	@Test
	void Test_or()
	{
		Word a = new Word();
        a.set(4);
        Word b= new Word ();
        b.set(-4);
        ALU ad= new ALU(a,b);
        Bit or[]= new Bit[] {new Bit(true),new Bit(false),new Bit(false),new Bit(true)};
        ad.doOperation(or);
        assertEquals(ad.Result.getSigned(),-4);//The last significant bit will be 1 as its or
        
       
        a.set(1);
        b.set(0);
         ad= new ALU(a,b);
        ad.doOperation(or);
        assertEquals(ad.Result.getSigned(),1);//1 or 0 is 1
        
        
        a.set(0);
        b.set(0);
         ad= new ALU(a,b);
        ad.doOperation(or);
        assertEquals(ad.Result.getSigned(),0);//0 or 0 is 0
        a.set(1);
        b.set(1);
         ad= new ALU(a,b);
        ad.doOperation(or);
        assertEquals(ad.Result.getSigned(),1);//1 or 1 is 1
        a.set(0);
        b.set(1);
         ad= new ALU(a,b);
        ad.doOperation(or);
        assertEquals(ad.Result.getSigned(),1);//0 or 1 is 1
	}
	@Test
	void Test_and()
	{
		Word a = new Word();
        a.set(4);
        Word b= new Word ();
        b.set(-4);
        ALU ad= new ALU(a,b);
        Bit and[]= new Bit[] {new Bit(true),new Bit(false),new Bit(false),new Bit(false)};
        ad.doOperation(and);
        assertEquals(ad.Result.getSigned(),4);//The last significant bit will be 0 as its "and"
        
       
        a.set(1);
        b.set(0);
         ad= new ALU(a,b);
        ad.doOperation(and);
        assertEquals(ad.Result.getSigned(),0);//1 and 0 is 0
        
        
        a.set(0);
        b.set(0);
         ad= new ALU(a,b);
        ad.doOperation(and);
        assertEquals(ad.Result.getSigned(),0);//0 and 0 is 0
        a.set(1);
        b.set(1);
         ad= new ALU(a,b);
        ad.doOperation(and);
        assertEquals(ad.Result.getSigned(),1);//1 and 1 is 1
        a.set(0);
        b.set(1);
         ad= new ALU(a,b);
        ad.doOperation(and);
        assertEquals(ad.Result.getSigned(),0);//0 and 1 is 0
	}
	@Test
	void TestXor()
	{
		Word a = new Word();
        a.set(4);
        Word b= new Word ();
        b.set(-4);
        ALU ad= new ALU(a,b);
        Bit Xor[]= new Bit[] {new Bit(true),new Bit(false),new Bit(true),new Bit(false)};
        ad.doOperation(Xor);
        assertEquals(ad.Result.getSigned(),-8);//The last significant bit will be 0 as its "and"
        
       
        a.set(1);
        b.set(0);
        ad= new ALU(a,b);
        ad.doOperation(Xor);
        assertEquals(ad.Result.getSigned(),1);//1 Xor 0 is 1
        
        
        a.set(0);
        b.set(0);
         ad= new ALU(a,b);
        ad.doOperation(Xor);
        assertEquals(ad.Result.getSigned(),0);//0 Xor 0 is 0
        a.set(1);
        b.set(1);
         ad= new ALU(a,b);
        ad.doOperation(Xor);
        assertEquals(ad.Result.getSigned(),0);//1 Xor 1 is 0
        a.set(0);
        b.set(1);
         ad= new ALU(a,b);
        ad.doOperation(Xor);
        assertEquals(ad.Result.getSigned(),1);//0 Xor 1 is 1
	}
	@Test
	void test_not()
	{

		Word a = new Word();
        a.set(4);//f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f
        Word b= new Word ();
        b.set(-4);
        ALU ad= new ALU(a,b);
        Bit not[]= new Bit[] {new Bit(true),new Bit(false),new Bit(true),new Bit(true)};
        ad.doOperation(not);
        assertEquals(ad.Result.toString(),"t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,f,t,t");//The last significant bit will be 0 as its "and"
	}
	@Test 
	void test_left()
	{
		Word a = new Word();
        a.set(4);
        Word b= new Word ();
        b.set(1);
        ALU ad= new ALU(a,b);
        Bit left[]= new Bit[] {new Bit(true),new Bit(true),new Bit(false),new Bit(false)};
        ad.doOperation(left);
        assertEquals(ad.Result.getSigned(),8);//The left shift by 1 of the 4 is 8
        a.set(4);
        b.set(2);
         ad= new ALU(a,b);
        ad.doOperation(left);
        assertEquals(ad.Result.getSigned(),16);//The left shift by 2 of the 4 is 16
	}	
	@Test 
	void test_right()
	{
		Word a = new Word();
        a.set(4);
        Word b= new Word ();
        b.set(1);
        ALU ad= new ALU(a,b);
        Bit right[]= new Bit[] {new Bit(true),new Bit(true),new Bit(false),new Bit(true)};
        ad.doOperation(right);
        assertEquals(ad.Result.getSigned(),2);//The right shift by 1 of the 4 is 2
        a.set(4);
        b.set(2);
         ad= new ALU(a,b);
        ad.doOperation(right);
        assertEquals(ad.Result.getSigned(),1);//The right shift by 2 of the 4 is 1
	}
	
	@Test
	void Testadd()
	{
		Word a = new Word();
        a.set(2);
        Word b= new Word ();
        b.set(3);
       ALU ad= new ALU(a,b);
       Bit add[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
       ad.doOperation(add);
       assertEquals(ad.Result.getSigned(),2+3);//This is add2
       
       a.set(4);
       b.set(-6);
        ad= new ALU(a,b);
       ad.doOperation(add);
       assertEquals(ad.Result.getSigned(),4+(-6));

       //The tests for add4
       
       Word one = new Word();
       one.set(10);
       Word two= new Word ();
       two.set(15);
       Word three= new Word ();
       three.set(1);
       Word four= new Word ();
       four.set(1);
       Word sum=ad.add4(one, two, three, four);
        assertEquals(sum.getSigned(),10+15+1+1);
        
        
        
        one.set(1);
        two.set(1);
        three.set(-1);
        four.set(1);
         sum=ad.add4(one, two, three, four);
         assertEquals(sum.getSigned(),1+1-1+1);
         
         one.set(0);
         two.set(1);
         three.set(1);
         four.set(1);
          sum=ad.add4(one, two, three, four);
          assertEquals(sum.getSigned(),0+1+1+1);
	}
	
	@Test
	void Testsub()
	{
		Word a = new Word();
        a.set(2);
        Word b= new Word ();
        b.set(3);
        ALU ad= new ALU(a,b);
        Bit sub[]= new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(true)};
        ad.doOperation(sub);
        assertEquals(ad.Result.getSigned(),2-3);
        a.set(23);
        b.set(13);
        ad= new ALU(a,b);
        ad.doOperation(sub);
        assertEquals(ad.Result.getSigned(),23-13);
        a.set(2323);
        b.set(13);
        ad= new ALU(a,b);
        ad.doOperation(sub);
        assertEquals(ad.Result.getSigned(),2323-13);
        a.set(3);
        b.set(-2);
        ad= new ALU(a,b);
        ad.doOperation(sub);
        assertEquals(ad.Result.getSigned(),5);
	}
	@Test
	void multiply()
	{
		Word a = new Word();
        a.set(2);
        Word b= new Word ();
        b.set(3);
        ALU ad= new ALU(a,b);
        Bit multiply[]= new Bit[] {new Bit(false),new Bit(true),new Bit(true),new Bit(true)};
        ad.doOperation(multiply);
        assertEquals(ad.Result.getSigned(),3*2);
        
        a.set(3);
        b.set(-2);
        ad= new ALU(a,b);
        ad.doOperation(multiply);
        assertEquals(ad.Result.getSigned(),3*-2);
        
        a.set(234);
        b.set(-2);
        ad= new ALU(a,b);
        ad.doOperation(multiply);
        assertEquals(ad.Result.getSigned(),234*-2);
        
        a.set(34);
        b.set(63);
        ad= new ALU(a,b);
        ad.doOperation(multiply);
        assertEquals(ad.Result.getSigned(),34*63);
        
        a.set(0);
        b.set(3223);
        ad= new ALU(a,b);
        ad.doOperation(multiply);
        assertEquals(ad.Result.getSigned(),0);
        
        a.set(1);
        b.set(1);
        ad= new ALU(a,b);
        ad.doOperation(multiply);
        assertEquals(ad.Result.getSigned(),1);
	}
	@Test
	void Memory_test_And_increment_test()
	{
		 MainMemory MM= new MainMemory();
		 String str[]= new String[] {"01101010101010101010010101010101","00101010101010101010010101010101"};
		 MM.load(str);//We are loading the above strings in the Main memory
		 Word read= new Word();
		 read.set(0);// We want to read what is written on the memory at 0
	     Word readed=MM.read(read);
		 assertEquals("f,t,t,f,t,f,t,f,t,f,t,f,t,f,t,f,t,f,t,f,f,t,f,t,f,t,f,t,f,t,f,t",readed.toString());//Checking if the written and read memory matches
		 read.set(1);
		 assertEquals("f,f,t,f,t,f,t,f,t,f,t,f,t,f,t,f,t,f,t,f,f,t,f,t,f,t,f,t,f,t,f,t",MM.read(read).toString());//Checking if the written and read memory matches

		 
		 
		 Word Address= new Word();
		 Address.set(0);
		 Word value= new Word();
		 value.set(23);
		 MM.write(Address,value);//We are Writing 23 at address 0
		 read.set(0);
	     readed=MM.read(read);// now we are reading the memory again
		 assertEquals(value.toString(),readed.toString());	
		 String str1[]= new String[] {"00101010101000001000000101010101"}; 
		 
		 
		 
		 
		 MM.load(str1);//trying to load a new array of sting into the memory
		 read.set(0);
	     readed=MM.read(read);
		 assertEquals("f,f,t,f,t,f,t,f,t,f,t,f,f,f,f,f,t,f,f,f,f,f,f,t,f,t,f,t,f,t,f,t",readed.toString());	 
		 /**
		  * Below is the test for the increase method of the word.
		  */
		 Word word= new Word();
		 word.set(-20000);
		 for(int i=-20000;i<=20000;i=i+1)
		 {
			 assertEquals(i,word.getSigned());

			 word.increment();
		 }
	}
	@Test
	void Test_Computer() throws FileNotFoundException
	{
	   File file= new File("testprogram");
	   Scanner Reader1 = new Scanner(file);
	   int instructions_count=0;
	   while(Reader1.hasNextLine()) 
	      {
		   Reader1.nextLine();
		   instructions_count++;
		   }
	   
	   Scanner Reader = new Scanner(file);
	   String[] instructions= new String[instructions_count];
	   int i=0;
	   while (Reader.hasNextLine()) 
	      {
	         instructions[i++] = Reader.nextLine();
	      }
         Processor pro= new Processor();
         pro.mm.load(instructions);
         pro.run();
	   
	}
}