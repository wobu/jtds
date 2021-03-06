//
// Copyright 1998 CDS Networks, Inc., Medford Oregon
//
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
// 1. Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in the
//    documentation and/or other materials provided with the distribution.
// 3. All advertising materials mentioning features or use of this software
//    must display the following acknowledgement:
//      This product includes software developed by CDS Networks, Inc.
// 4. The name of CDS Networks, Inc.  may not be used to endorse or promote
//    products derived from this software without specific prior
//    written permission.
//
// THIS SOFTWARE IS PROVIDED BY CDS NETWORKS, INC. ``AS IS'' AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED.  IN NO EVENT SHALL CDS NETWORKS, INC. BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
// OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
// HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
// LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
// OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE.
//


package com.internetcds.util;


/**
 * A simple class to convert a raw buffer to a hex dump
 *
 * @version $Id: HexDump.java,v 1.2 2001-08-31 12:47:20 curthagenlocher Exp $
 * @author Craig Spannring
 */ 
public class HexDump
{
   public static final String cvsVersion = "$Id: HexDump.java,v 1.2 2001-08-31 12:47:20 curthagenlocher Exp $";

   static String byteToHexString(byte b)
   {
      return intToHexString(b, 2, '0');
   } /* byteToHexString()  */
   
   static String intToHexString(int num, int width, char fill)
   {
      String   result = "";
      int      i;

      if (num==0)
      {
         result = "0";
         width--;
      }
      else
      {
         while(num!=0 && width>0)
         {
            String  tmp = Integer.toHexString(num & 0xf);
            result = tmp + result;
            num = (num>>4);
            width--;
         }
      }
      for(; width>0; width--)
      {
         result = fill + result;
      }
      return result;
   } /* intToHexString()  */

   public static String hexDump(byte data[])
   {
      return hexDump(data, data.length);
   }


   public static String hexDump(byte data[], int length)
   {
      String     str;
      int        i;
      int        j;  
      final int  bytesPerLine = 16;
      String     result = "";

      
      for(i=0; i<length; i+=bytesPerLine)
      {
         // print the offset as a 4 digit hex number
         result = result + intToHexString(i, 4, '0') + "  ";

         // print each byte in hex
         for(j=i; j<length && (j-i)<bytesPerLine; j++)
         {
            result = result + byteToHexString(data[j]) + " ";
         }

         // skip over to the ascii dump column
         for(; 0!=(j % bytesPerLine); j++)
         {
            result = result +  "   ";
         }
         result = result + "  |";         

         // print each byte in ascii
         for(j=i; j<length && (j-i)<bytesPerLine; j++)
         {
            if (((data[j] & 0xff) > 0x001f) && ((data[j] & 0xff) < 0x007f))
            {
               Character ch = new Character((char) data[j]);
               result = result + ch;
            }
            else
            {
               result = result + ".";
            }
         }
         result = result + "|\n";
      }
      return result;
   } /* hexDump()  */
}
