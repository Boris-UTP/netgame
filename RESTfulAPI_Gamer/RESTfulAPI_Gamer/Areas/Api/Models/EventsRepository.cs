using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTfulAPI_Gamer.Areas.Api.Models
{
    public class EventsRepository
    {

        static BDGamerEntities dataContext = new BDGamerEntities();

        public  bool InsertEvent(Event events)
        {

            try
            {
                dataContext.Event.Add(events);
                dataContext.SaveChanges();
                return true;
            }
            catch (Exception)
            {
                return false;
            }

        }

          
        public List<Events> GetDetailEventById(int IdEvent)
        {

            var query = from e in dataContext.Event
                         where e.State == 1 && e.IdEvent == IdEvent
                         join c in dataContext.Cabin on e.IdCabin equals c.IdCabin 
                         select new Events
                         {

                             Title = e.Title,
                             Description = e.Description,
                             DateStart = (DateTime)e.DateStart,
                             DateEnd = (DateTime)e.DateEnd,
                             DateEvent = (DateTime)e.DateEvent,
                             Address = c.Address

                          };

            return query.ToList();
        }



        public List<Events> GetAllEvents()
        {
            var query = from p in dataContext.Event
                        where p.State == 1
                        orderby p.IdEvent
                        select new Events
                        {
                            Title = p.Title,
                            Description = p.Description,
                            DateEvent = (DateTime)p.DateEvent,
                           

                        };

            return query.ToList();
        }

        

        public class Events
        {
           

            public string Title { get; set; }
            public string Description { get; set; }
            public DateTime DateEvent { get; set; }
            public DateTime DateStart { get; set; }
            public DateTime DateEnd { get; set; }
            public string Address { get; set; }


        }

    }
}