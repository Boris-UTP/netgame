using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTfulAPI_Gamer.Areas.Api.Models
{
    public class GamersRepository
    {


        static BDGamerEntities dataContext = new BDGamerEntities();

        public  bool InsertGamer(Gamer gamer)
        {

            try
            {
                dataContext.Gamer.Add(gamer);
                dataContext.SaveChanges();
                return true;
            }
            catch (Exception)
            {
                return false;
            }

        }




        public  bool UpdateGamer(Gamer gamer)
        {

            try
            {
                var gam = (from Gamer in dataContext.Gamer
                           where Gamer.IdGamer == gamer.IdGamer
                           select Gamer).SingleOrDefault();
                gam.Name = gamer.Name;
                gam.LastName = gamer.LastName;
                gam.Phone = gamer.Phone;
                gam.Address = gamer.Address;
                gam.NickName = gamer.NickName;
              //  gam.Age = gamer.Age;
               // gam.Gender = gamer.Gender;
                dataContext.SaveChanges();
                return true;

            }
            catch (Exception)
            {
                return false;
            }

        }


    }
}