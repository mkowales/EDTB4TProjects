package com.edt.b4t.projects;

import java.util.ArrayList;
import java.util.HashSet;

import com.edt.b4t.B4TProjects;
import com.edt.b4t.B4TTimesheets;
import com.edt.b4t.util.DemoFile;
import com.edt.b4t.util.Str;

public class EDTB4TProjects
{
	public static void main(String[] args)
	{
		EDTB4TProjects projects = new EDTB4TProjects();
		projects.getProjects();
	}
	
	public B4TProjects getProjects()
	{
	    DemoFile demo = new DemoFile("EDTB4TProjects.html");
	    
		String projPrefs = Str.convertToURL("?$filter=billingMethod eq 'Hourly' " +
		                                "AND createdDate ge '2013-09-01' AND status eq 'Open' " +
		                                "AND projectType ne 'Internal'"),
		        sheetPrefs = Str.convertToURL("?$filter=createdDate ge '2017-11-01'");

		int count = 0;
		
		B4TProjects projects = new B4TProjects(projPrefs);
		B4TTimesheets sheets = null;
		
		System.out.println(projects.getIds().size());
		
		String report = Str.CR + "<table cellpadding=\"0\" border=\"1\">" +
                        Str.CR + "<thead>" +
                        Str.CR + "<tr>" +
                        Str.CR + "<th align=\"center\">Count</th>" +
                        Str.CR + "<th align=\"center\">Project</th>" +
                        Str.CR + "<th align=\"center\">Client</th>" +
                        Str.CR + "<th align=\"center\">Type</th>" +
                        Str.CR + "<th align=\"center\">Time Sheet User</th>" +
                        Str.CR + "</tr>" +
                        Str.CR + "</thead>";
		
//		for (int index = 0; index < projects.getIds().size(); index++)
		for (int index = 0; index < 5; index++)
		{
		    sheets = new B4TTimesheets(sheetPrefs +
                    Str.convertToURL(" AND projectId eq '" + projects.getIds().get(index) + "'&orderby=userName desc"));
		    
		    if (0 == sheets.getIds().size())
		        continue;
		    
			report += "<tr><td align=\"center\">" + ++count + "</td>"
            			+ Str.CR +"<td align=\"center\">" + projects.getProjectNames().get(index) + "</td>"
            			+ Str.CR +"<td align=\"center\">" + projects.getClientNames().get(index) + "</td>"
            			+ Str.CR +"<td align=\"center\">" + projects.getProjectTypes().get(index) + "</td>"
//            			+ Str.CR +"<t align=\"center\"d>" + projects.getStatuses().get(index) + "</td>"
            			+ Str.CR +"<td align=\"center\">";
			
			ArrayList<String> names = sheets.getUserNames();
			
			HashSet<String> uniqueNames = new HashSet<>(names);
			
			if (uniqueNames.contains(","))
			    report += Str.CR + uniqueNames.toString() + "<br>";
			else
			    report += Str.CR + uniqueNames.toString();
			    
//			String str = uniqueNames.toString();
			
//			str = str.replace('[', '');
//			str = str.replaceAll("[", "<br>");
//			str = str.replaceAll(",", "<br>");
//			str = str.replaceAll("]", "");
			
//			report += str + "</td>";
			report += "</td>";
		}

        demo.writeLine(this.getContent());
		demo.writeLine(report);
		demo.writeLine(this.close());
        demo.close();

        System.out.println("Done");
		return projects;
	}
	
    private String close()
    {
        return "</table></body></html>";
    }

    private String getContent()
    {
        String content = 
                "<!DOCTYPE html>" +
                "\r\n<html>" +
                "\r\n<head>" +
                "\r\n<title>EDT Projects</title>" +
                "\r\n<link rel=\"icon\" href=\"https://edtssl.eagledream-hosting.com/wp-content/uploads/2015/02/edt_fav_2.png\"/>" +
                "\r\n</head>" +
                "\r\n<body>" +
                "\r\n<center>";

        String body = 
                "\r\n<h2>EDT Projects</h2>"
//                "<img src=" + System.getProperty("image.header") + ">"
                    ;

        return content + body;
    }
}
