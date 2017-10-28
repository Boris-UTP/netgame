using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTfulAPI_Gamer.Areas.Api.Models
{
    public class CabinsRepository 
    {
        
        static BDGamerEntities dataContext = new BDGamerEntities();

        public  bool InsertCabin(Cabin cabin)
        {

            try
            {
                dataContext.Cabin.Add(cabin);
                dataContext.SaveChanges();
                return true;
            }
            catch (Exception)
            {
                return false;
            }

        }


        public  bool UpdateCabin(Cabin cabin)
        {

            try

            {




                var cab = (from Cabin in dataContext.Cabin
                           where Cabin.IdCabin == cabin.IdCabin
                           select Cabin).SingleOrDefault();
                cab.Name = cabin.Name;
                cab.Address = cabin.Address;
                cab.Latitude = cabin.Latitude;
                cab.Longitude = cabin.Longitude;
               // cab.StateAttention = cabin.StateAttention;
               // cab.State = cabin.State;

                dataContext.SaveChanges();
                return true;
            }
            catch (Exception)
            {
                return false;
            }

        }

        public  bool UpdateUbicationCabin(Cabin cabin)
        {

            try
            {
                var cab = (from Cabin in dataContext.Cabin
                           where Cabin.IdCabin == cabin.IdCabin
                           select Cabin).SingleOrDefault();
                cab.Address = cabin.Address;
                cab.Latitude = cabin.Latitude;
                cab.Longitude = cabin.Longitude;               
                dataContext.SaveChanges();
                return true;
            }
            catch (Exception)
            {
                return false;
            }

        }


        public List<Cabins> GetAllUbicationsCabins()
        {

  
            var query = from p in dataContext.Cabin
                        where p.State == 1
                        orderby p.Name
                        select new Cabins
                        {
                            Name = p.Name,
                            Address = p.Address,
                            Latitude = p.Latitude,
                            Longitude = p.Longitude

                        };

            return query.ToList();
        }


        public class Cabins
        {
            public string Name { get; set; }
            public string Address { get; set; }
            public string Latitude { get; set; }
            public string Longitude { get; set; }
        }







    }
}