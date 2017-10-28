using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTfulAPI_Gamer.Areas.Api.Models
{

    public class PublicationsRepository
    {

        static BDGamerEntities dataContext = new BDGamerEntities();

        public  bool InsertPublication(Publication publication)
        {

            try
            {
                dataContext.Publication.Add(publication);
                dataContext.SaveChanges();
                return true;
            }
            catch (Exception)
            {
                return false;
            }

        }

        

        public List<Publications> GetDetailPublicationById(int IdPublication)
        {

            var query = from c in dataContext.Comment
                        where c.State == 1 && c.IdPublication == IdPublication
            //            join p in dataContext.Publication on c.IdPublication equals p.IdPublication
                        select new Publications
                        {

                           
                            Description = c.Description,             
                            Mylike = (int) c.Mylike

                        };

            return query.ToList();
        }


        public List<Publications> SearchPublicationById(int IdGame)
        {
            var query = from p in dataContext.Publication
                        where p.State == 1 && p.idGame == IdGame 
                        orderby p.IdPublication
                        select new Publications
                        {
                            Title = p.Title,
                            Description = p.Description,
                            DateRegister = (DateTime ) p.DateRegister

                        };

            return query.ToList();
        }


        public class Publications
        {
            public string Title { get; set; }
            public string Description { get; set; }
            public DateTime   DateRegister { get; set; }
            public int Mylike { get;set; }
          
        }






    }
}