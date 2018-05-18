function listbox_moveacross(sourceID, destID) {
    var src = document.getElementById(sourceID);
    var dest = document.getElementById(destID);
    var picked1 = false;
    for (var count=0;count<src.options.length;count++){
            		if(src.options[count].selected==true){picked1=true;}
            	}
            
            	if(picked1==false){alert("Please select an option to move.");return;}
            
            	for(var count=0;count<src.options.length;count++){
            		if(src.options[count].selected==true){var option=src.options[count];
            			var newOption=document.createElement("option");
            			newOption.value=option.value;
            			newOption.text=option.text;
            			try{dest.add(newOption.value,null);
            			src.remove(count,null);
            		}
            			catch(error){dest.add(newOption);src.remove(count);
            		}
            		count--;
            		}
            	}}         
           
         function dynamicdropdown(listindex)
         {
         document.getElementById("prdvalue").length = 0;
         switch (listindex)
             {

         case "01" :
                document.getElementById("prdvalue").options[0]=new Option("CBL","cbl");
                document.getElementById("prdvalue").options[1]=new Option("FLP","flp");
                document.getElementById("prdvalue").options[2]=new Option("FOC","foc");
         document.getElementById("prdvalue").options[3]=new Option("HPT","hpt");
                document.getElementById("prdvalue").options[4]=new Option("LTB","ltb");
                document.getElementById("prdvalue").options[5]=new Option("LTC","ltc");
         document.getElementById("prdvalue").options[6]=new Option("LTR","ltr");
                document.getElementById("prdvalue").options[7]=new Option("MC","mc");
                document.getElementById("prdvalue").options[8]=new Option("OED","oed");
         document.getElementById("prdvalue").options[9]=new Option("ORL","orl");
                 break;
         case "2" :
                document.getElementById("prdvalue").options[0]=new Option("92996","1");
                document.getElementById("prdvalue").options[1]=new Option("67784","2");
                document.getElementById("prdvalue").options[2]=new Option("01595","3");
         document.getElementById("prdvalue").options[3]=new Option("11001","4");
                document.getElementById("prdvalue").options[4]=new Option("51003","5");
                document.getElementById("prdvalue").options[5]=new Option("08050","6");
         document.getElementById("prdvalue").options[6]=new Option("31010","7");
                document.getElementById("prdvalue").options[7]=new Option("08040","8");
                document.getElementById("prdvalue").options[8]=new Option("70103","9");
         document.getElementById("prdvalue").options[9]=new Option("30101","10");
                 break;
         case "3" :
                document.getElementById("prdvalue").options[0]=new Option("ENVigor","envi");
                document.getElementById("prdvalue").options[1]=new Option("ADRS","adr");
                document.getElementById("prdvalue").options[2]=new Option("S DRIVE","sdr");
         document.getElementById("prdvalue").options[3]=new Option("V262","v26");
                document.getElementById("prdvalue").options[4]=new Option("YK520","yk5");
                document.getElementById("prdvalue").options[5]=new Option("ADVANV105S","ad10");
         document.getElementById("prdvalue").options[6]=new Option("S208","5208");
                document.getElementById("prdvalue").options[7]=new Option("A008RS","a08");
         document.getElementById("prdvalue").options[8]=new Option("Y372","y32");
                 break;
                 default: e.preventDefault();
         }
    
            return true;
         }
