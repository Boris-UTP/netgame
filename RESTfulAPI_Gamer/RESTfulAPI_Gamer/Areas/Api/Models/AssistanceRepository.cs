using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTfulAPI_Gamer.Areas.Api.Models
{
    public class AssistanceRepository
    {

        static BDGamerEntities dataContext = new BDGamerEntities();

        public bool InsertAssistance(Assistance assistance)
        {

            try
            {
                dataContext.Assistance.Add(assistance);
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