package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._
import common._
import http._
import sitemap._
import Loc._
import sk.yin.web.snippet.GraphService


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    // where to search snippet
    LiftRules.addToPackages("sk.yin.web")

    // Build SiteMap
    val entries = List(
      Menu.i("Home") / "index",
      Menu.i("Levenshtein") / "levenstein",
      Menu.i("Graph's") / "dijkstra"
      //,
      //Menu.i("Computer Language") / "language"
    )

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries:_*))

    // Use jQuery 1.4
    LiftRules.jsArtifacts = net.liftweb.http.js.jquery.JQuery14Artifacts

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Web Services
    val services = List(GraphService);
    for (service <- services) {
    	LiftRules.dispatch.append(service);
    	LiftRules.statelessDispatchTable.append(service);
    }
  }
}
