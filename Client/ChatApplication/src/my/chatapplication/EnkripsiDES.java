/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.chatapplication;

/**
 *
 * @author Icha
 */
public class EnkripsiDES 
{
    public boolean[][] Key_Schedule(boolean key[])
    {
         boolean round_key_gen[][] = new boolean[KEY_NO][ROUND_KEY_LENGTH];
         if(key.length != KEY_LENGTH) 
         {
             System.out.printf("Key_Schedule: Wrong input. The key should be 64-bit in length\n");
             return round_key_gen;
         }
         
         boolean[] PC1_Permuted_Key = new boolean[PC1.length];
         for (int i=0;i<PC1.length;i++)
         {
             for (int j=0;j<PC1.length;j++)
             {
                 if (i==PC1[j]-1)
                 PC1_Permuted_Key[j]=key[i];
             }
         }
         
         boolean[] left = new boolean[PC1.length/2];
         boolean[] right = new boolean[PC1.length/2];
         boolean[] round_left = left;
         boolean[] round_right = right;
         
         for (int index=0;index<PC1.length/2;index++)
         {
             left[index] = PC1_Permuted_Key[index];
             right[index] = PC1_Permuted_Key[index+(PC1.length/2)];
         }
         
         boolean[] shifted_key = new boolean[PC1.length];
         
         for(int round_number=1;round_number<=KEY_NO;round_number++)
         {
             round_left = shift_array(round_left, des_shifts(round_number));
             round_right = shift_array(round_right, des_shifts(round_number));
             
             for(int index=0;index<PC1.length/2;index++)
             {
                 shifted_key[index] = round_left[index];
                 shifted_key[index+(PC1.length/2)] = round_right[index];
             }
             
             for (int i=0;i<shifted_key.length;i++)
             {
                 for (int j=0;j<PC2.length;j++)
                 {
                     if (i==PC2[j]-1) 
                         round_key_gen[round_number-1][j] = shifted_key[i];
                 }
             }            
         }
         
         return round_key_gen;
    }    
    
    public int des_shifts(int round_number)
    {
        switch(round_number)
        {
            case 1: 
                return 1;
            case 2: 
                return 1;
            case 3: 
                return 2;
            case 4: 
                return 2;
            case 5: 
                return 2;
            case 6: 
                return 2;
            case 7: 
                return 2;
            case 8: 
                return 2;
            case 9: 
                return 1;
            case 10: 
                return 2;
            case 11: 
                return 2;
            case 12: 
                return 2;
            case 13: 
                return 2;
            case 14: 
                return 2;
            case 15: 
                return 2;
            case 16: 
                return 1;
        }
        return 0;
    }
    
    public boolean[] shift_array(boolean array[], int shifts)
    {
        boolean shifted_array[] = new boolean[array.length];
        int ts = shifts;
        
        for(int i=0;i<array.length-shifts;i++)
        {
            if(ts!=0)
            {
                shifted_array[array.length-ts] = array[i];
                ts--;
            }
            shifted_array[i] = array[i+shifts];
        }
        return shifted_array;
    }
    
    public boolean[] Initial_Permutation(boolean block[])
    {
        boolean block_after_ip[] = new boolean[BLOCK_LENGTH];
        if(block.length != BLOCK_LENGTH)
        {
            System.out.printf("Initial_Permutation:Wrong input! The input block for Initial Permutation should have length  64 bits\\n");
            return block_after_ip;
        }
        
        for (int i=0;i<IP.length;i++)
        {
            block_after_ip[i] = block[IP[i]-1];
        }
        return block_after_ip;
    }
    
    public boolean[] Expansion(boolean array[])
    {
        boolean array_after_expand[] = new boolean[ROUND_KEY_LENGTH];
        if(array.length != HALF_LENGTH)
        {
            System.out.printf("Expansion: Wrong input! The input arry for Expansion should have length  \" +HALF_LENGTH+ \" bits\\n");
            return array_after_expand;
        }
        
        for(int i = 0; i < ROUND_KEY_LENGTH; i++)
        {
            array_after_expand[i]=array[E[i]-1];
        }
        return array_after_expand;
    }
    
    public boolean[] XOR(boolean array_1[],  boolean array_2[])
    {
        if(array_1.length != array_2.length)
        {
            System.out.printf("XOR: Wrong input for XOR function! Please check your input! Only support the case that the arrays are with equal length\n");
            return new boolean[1];
        }
        
        boolean result_xor[] = new boolean[array_1.length];
        
        for (int i = 0; i < array_1.length; i++)
        {
            result_xor[i] = array_1[i] ^ array_2[i];
        }
        return result_xor;
    }
    
    public boolean[] SboxesSubstitution(boolean array[])
    {
        boolean array_after_substitution[] = new boolean[HALF_LENGTH];
        if(array.length != ROUND_KEY_LENGTH)
        {
            System.out.printf("SboxesSubstitution: Wrong input! The input arry for SboxesSubstitution should have length  " +ROUND_KEY_LENGTH+ " bits\n");
            return array_after_substitution;
        }
        
        int iterator = 0;
        boolean[][] blocks = new boolean[8][6];
        for (int i = 0; i < 8; i++)
        {
            for (int j=0;j<6;j++)
            {
                blocks[i][j] = array[iterator];
                iterator++;
            }
        }
        
        int[][] coords = new int[8][2];
        
        for(int i=0;i<blocks.length;i++)
        {
            for (int j=0;j<blocks[0].length;j++)
            {
                if (j==0 && blocks[i][j]) 
                    coords[i][0] += 2;
                if (j==5 && blocks[i][j]) 
                    coords[i][0] += 1;
                if (j==1 && blocks[i][j]) 
                    coords[i][1] += 8;
                if (j==2 && blocks[i][j]) 
                    coords[i][1] += 4;
                if (j==3 && blocks[i][j]) 
                    coords[i][1] += 2;
                if (j==4 && blocks[i][j]) 
                    coords[i][1] += 1;
            }
        }
        
        int sbox_sub[] = new int[8];
        int[][][] all_sboxes = glob_sboxes();
        
        for (int i=0;i<all_sboxes.length;i++)
        {
            sbox_sub[i] = all_sboxes[i][coords[i][0]][coords[i][1]];
        }
        
        return array_after_substitution;
    }
    
    public int boolean_array_to_integer(boolean[] array)
    {
        String bit_string = "";
        
        for (int i=0;i<array.length;i++)
        {
            if (array[i]) 
                bit_string += '1';
            
            else bit_string += '0';
        }
        
        return (Integer.parseInt(bit_string, 2)); 
    }
    
    public boolean[] values_to_boolean_array(int[] subs)
    {
        String concat_values = "";
        for (int i=0; i < subs.length; i++)
        {
            concat_values += String.format("%4s", Integer.toBinaryString(subs[i])).replace(' ', '0');
        }
        
        return (getBooleanArray(concat_values));
    }
    
    public int[][][] glob_sboxes()
    {
        int[][][] masterbox = new int[8][4][8];
        masterbox[0]=S1;
        masterbox[1]=S2;
        masterbox[2]=S3;
        masterbox[3]=S4;
        masterbox[4]=S5;
        masterbox[5]=S6;
        masterbox[6]=S7;
        masterbox[7]=S8;
        
        return masterbox;
    }
    
    public boolean[] Permutation_f(boolean array[])
    {
        boolean array_after_permutation[] = new boolean[HALF_LENGTH];
        if(array.length != HALF_LENGTH)
        {
            System.out.printf("Permutation: Wrong input! The input arry for Permutation should have length  " +HALF_LENGTH+ " bits\n");
            return array_after_permutation;
        }
        
        for (int i = 0; i < HALF_LENGTH; i++)
        {
            array_after_permutation[i] = array[P[i]-1];
        }
        
        return array_after_permutation;
    }
    
    public boolean[] One_Round(boolean block[], boolean round_key[])
    {
        boolean block_after_one_round[] = new boolean[BLOCK_LENGTH];
        
        if(block.length != BLOCK_LENGTH)
        {
            System.out.printf("One_Round: Wrong input! The input block for one round should have length  64 bits\n");
            return block_after_one_round;
        }
        
        if(round_key.length != ROUND_KEY_LENGTH)
        {
            System.out.printf("One_Round: Wrong input! The round key should have length  "+ ROUND_KEY_LENGTH+" bits\n");
            return block_after_one_round;
        }
        
        boolean[] left = new boolean[HALF_LENGTH];
        boolean[] right = new boolean[HALF_LENGTH];
        boolean[] new_left = new boolean[HALF_LENGTH];
        boolean[] new_right = new boolean[HALF_LENGTH];
        
        for (int i = 0; i < BLOCK_LENGTH; i++)
        {
            if (i < HALF_LENGTH) 
                left[i] = block[i];
            else 
                right[i-HALF_LENGTH] = block[i];
        }
        
        new_left = right;
        new_right = XOR(left, Permutation_f(SboxesSubstitution(XOR(Expansion(right),round_key))));
        
        for (int i=0; i < BLOCK_LENGTH; i++)
        {
            if (i < HALF_LENGTH) 
                block_after_one_round[i] = new_left[i];
            else 
                block_after_one_round[i] = new_right[i-HALF_LENGTH];
        }
        
        if (last_key)
        {
            for (int i=0; i < BLOCK_LENGTH; i++)
            {
                if (i < HALF_LENGTH) 
                    block_after_one_round[i] = new_right[i];
                else 
                    block_after_one_round[i] = new_left[i-HALF_LENGTH];
            }
        }
        
        return block_after_one_round;
    }
    
    public boolean[] Inverse_IP(boolean block[])
    {
        boolean block_inverse_ip[] = new boolean[BLOCK_LENGTH];
        
        if(block.length != BLOCK_LENGTH)
        {
            System.out.printf("Inverse_IP: Wrong input! The input block for Inverse IP should have length  64 bits\n");
            return block_inverse_ip;
        }
        
        for (int i = 0; i < IP_INVERSE.length; i++)
        {
            block_inverse_ip[i] = block[IP_INVERSE[i]-1]; 
        }
        
        return block_inverse_ip;
    }
    
    public boolean[] encryption_DES(boolean block[], boolean key[])
    {
        boolean cypher_text[]= new boolean[BLOCK_LENGTH];
        
        if(block.length != BLOCK_LENGTH)
        {
            System.out.printf("encryption_DES: Wrong input! The input block for DES encryption should have length  64 bits\n");
            return cypher_text;
        }
        
        if(key.length != BLOCK_LENGTH)
        {
            System.out.printf("encryption_DES: Wrong input! The key for DES encryption should have length  64 bits\n");
            return cypher_text;
        }
        
        cypher_text = Initial_Permutation(block);
        boolean[][] round_keys = Key_Schedule(key);
        
        for (int i=0; i < KEY_NO; i++)
        {
            if (i==15)  
                last_key = true;
            cypher_text = One_Round(cypher_text, round_keys[i]);
        }
        last_key = false;
        
        cypher_text = Inverse_IP(cypher_text);
        return cypher_text;
    }
    
    public boolean[] decryption_DES(boolean block[], boolean key[])
    {
        boolean plain_text[]= new boolean[BLOCK_LENGTH];
        
        if(block.length != BLOCK_LENGTH)
        {
            System.out.printf("decryption_DES: Wrong input! The input block for DES decryption should have length  64 bits\n");
            return plain_text;
        }
        
        if(key.length != BLOCK_LENGTH)
        {
            System.out.printf("decryption_DES: Wrong input! The key for DES decryption should have length  64 bits\n");
            return plain_text;
        }
        
        plain_text = Initial_Permutation(block);
        boolean[][] round_keys = Key_Schedule(key);
        
        int iteration = 15;
        
        for (int i=0; i < KEY_NO; i++)
        {
            if (i==15) 
                last_key = true;
            plain_text = One_Round(plain_text, round_keys[iteration]);
            iteration--;
        }
        last_key = false;
        
        plain_text = Inverse_IP(plain_text);
        
        return plain_text;
    }
    
    public void writeBooleanArrayToFile(FileWriter fw, boolean array[])
    {
        int i = 0;
        
        try
        {
            for(i=0;i<array.length;i++)
            {
                if(array[i])
                {
                    fw.write("1");
                }
                else 
                    fw.write("0");
                
                if((i+1) % BYTE_LENGTH == 0) 
                    fw.write(" ");
            }             
        }
        
        catch(IOException e)
        {
            System.out.printf("Exception when writing the output: "+e.toString()+"\n");
        }        
    }
    
    public boolean[] getBooleanArray(String input)
    {
        //int i = 0;
        int length = input.length();
        boolean array[] = new boolean[length];
        
        for(int i=0;i<length;i++)
        {
            if(input.charAt(i) == '1')
                array[i] = true;
            else array[i] = false;  
        }
  
    return array;
    }
}
