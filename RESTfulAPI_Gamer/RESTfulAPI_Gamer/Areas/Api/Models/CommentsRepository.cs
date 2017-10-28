using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTfulAPI_Gamer.Areas.Api.Models
{
    public class CommentsRepository
    {

        static BDGamerEntities dataContext = new BDGamerEntities();

        public  bool InsertComment(Comment comment)
        {

            try
            {
                dataContext.Comment.Add(comment);
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