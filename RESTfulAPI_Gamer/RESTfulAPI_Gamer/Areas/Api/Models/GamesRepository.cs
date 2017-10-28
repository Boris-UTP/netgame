using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTfulAPI_Gamer.Areas.Api.Models
{
    public class GamesRepository
    {

        static BDGamerEntities dataContext = new BDGamerEntities();

 
        public List<Games> GetAllGames()
        {
            var query = from p in dataContext.Game
                            where p.State == 1
                            orderby p.Name
                            select new Games
                            {
                                Name = p.Name,
                                Imagen = p.Imagen

                            };

            return query.ToList();
        }


        public class Games
        {
            public string Name { get; set; }
            public string Imagen { get; set; }
        }




    }
}