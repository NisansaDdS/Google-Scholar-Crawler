<html lang="en">
<head>
  <meta charset="utf-8" />


<link rel="stylesheet" href="https://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />  
<link rel="stylesheet" href="./scripts/peopleCSS.css" />  


  
<script type="text/javascript" src="https://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript">
if (typeof jQuery == 'undefined')
{
    document.write(unescape("%3Cscript src='jquery-1.9.1.js' type='text/javascript'%3E%3C/script%3E"));
}
</script>




<script type="text/javascript" src="https://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>


<script type="text/javascript" src="./scripts/people.js"></script>	





<style type="text/css"> 

#conference
{
font-style:italic;
font-size:15px;
}

#conferenceD
{
font-size:10px;
color:#AAAAAA;
}

#description
{
font-size:15px;
text-align:justify;
}


.ui-accordion .ui-accordion-header {
	display: block;
	cursor: pointer;
	position: relative;
	margin-top: 2px;
	padding: .1em .1em .1em 2em;
	font-size: 16px;
	min-height: 0; /* support: IE7 */
}

</style>


</head>
<body background="./images/background.png">
<div id="Research_accordion"></div>
<script>
 var processingHeaders = $("#Research_accordion > h3"); 
	var ids=[];
	for (i = 0; i < processingHeaders.length; i++) {
		ids.push($(processingHeaders[i]).attr('id'));    
	}

$(function() {
		$( "#Research_accordion" ).accordion(
		{
			collapsible: true,
			active: false,
			heightStyle: "content"
		}
		);   
	});
 </script>
<script type="text/javascript">
    jQuery(document).ready(function () {
        jQuery(document).click(function () {
            var frame = $('#maincontent1', window.parent.document);
            var height = jQuery(document).height();
            frame.height(height + 15);
        });
    });

</script>





</body>
</html>
