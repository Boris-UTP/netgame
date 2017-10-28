using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using System.Security.Cryptography;
using System.Text;
using System.IO;

namespace RESTfulAPI_Gamer.Areas.Api
{
    public class RSAProvider
    {

        public RSACryptoServiceProvider RSAService { get;set;}
       
        public string publicKey  = "<RSAKeyValue><Modulus>nG8ezXQOVT+0Jj2tKYZovzVAbl7FtdTju3AuQkAWT5MlkE5JYSXr7uJCzNWX2oLmvI3ZEST+yglJqmX2jYRj2xYLlmCdyY5zD/n/huo/rvHFOi5iyzsp/Ia4fvdSuMsODIN1+spMsD6//DUdV0jcEiKmX6yqmY4NqRlEyVumfTM=</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>";
        public string privateKey = "<RSAKeyValue><Modulus>nG8ezXQOVT+0Jj2tKYZovzVAbl7FtdTju3AuQkAWT5MlkE5JYSXr7uJCzNWX2oLmvI3ZEST+yglJqmX2jYRj2xYLlmCdyY5zD/n/huo/rvHFOi5iyzsp/Ia4fvdSuMsODIN1+spMsD6//DUdV0jcEiKmX6yqmY4NqRlEyVumfTM=</Modulus><Exponent>AQAB</Exponent><P>2POz9lVTkfFxl3cbns5jXeV+IRSTXEH9tLTbxtndQacoOpuZlLxkx3XPcZ5dZX09YLxq4q7ImCGQA0E5wpID3w==</P><Q>uJb9S6VdQCbt92lTcQwcKD1Fg6AbjivbMY0B7Shw8SKmhfOPi7IqZ0/aRPTgAxPVW2bv1SQQ0M74G67NcYYRLQ==</Q><DP>1rsPakc1+gOBrKsFOX+JtkwJNPDKkcv3Mr63sO++INcz6B5OOnrlPM6MTiayxlrZPz/+gvMANCM0CSSr6Lrg/Q==</DP><DQ>oUs+VcVPcGkT8ubUBKoiSH8RqOxUZ6ymweuGzWZTGkG+5LloqxaputCp1ytqOwYvnsiZtNLo5qyTXA0oc5REmQ==</DQ><InverseQ>JWYrqHKRmjDBfSPT817JV6jS9lTMesKIt3ceSuYe6j3dC66GCr4U8MiWzhKuH3qBsMCH2QkRJVwOPX7ZGTdJsw==</InverseQ><D>MaiEmquwsRcb1NfXVEs6jRgWb/LdyC7ga+vcg2Ncsb4AOqLfYFttXdOtaOCU5+SN4mUU9wBLcU8pd1IOxjW0jKQnVpQ3Ke8Ayuf2YtgNLtR4KjDieFKYpqaVgfOgX3Yek4x/QScvQEeWKwHITRaKODEOflBK/M/LQ2Cz7z4nUqE=</D></RSAKeyValue>";

        public  RSAProvider()
        {

            this.RSAService = new RSACryptoServiceProvider();

        }

        public byte[] CreatePublicKey() {
            string xmlPublicKey = this.RSAService.ToXmlString(false);
            return Encoding.ASCII.GetBytes(xmlPublicKey);    
        }


        public byte[] CreatePrivateKey()
        {
            string xmlPrivateKey = this.RSAService.ToXmlString(true);
            return Encoding.ASCII.GetBytes(xmlPrivateKey);
        }


    
        public  string  EncripText (string Text){

            RSACryptoServiceProvider RSA = new RSACryptoServiceProvider(1024);
            RSA.FromXmlString(publicKey);
            byte[] EncripData = RSA.Encrypt(Encoding.ASCII.GetBytes(Text), false);
            string textoEncriptado = Convert.ToBase64String(EncripData);

            return textoEncriptado;

        }


        public string  DecripText(string EncripText)
        {

            RSACryptoServiceProvider RSA = new RSACryptoServiceProvider(1024);
            RSA.FromXmlString(privateKey);         
            byte[] DecripData = RSA.Decrypt(Convert.FromBase64String(EncripText), false);
            string textoDesencriptado = Encoding.ASCII.GetString(DecripData);

            return textoDesencriptado;

        }

        
    }
}