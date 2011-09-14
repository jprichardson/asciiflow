<!doctype html>

<html>
<head>
<meta name="Description"
	content="Simple web based ascii flow diagram drawing tool, draw boxes, lines, arrows and type, then export straight to text/html.">

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<title>Asciiflow - ASCII Flow Diagram Tool</title>

<link id="page_favicon" href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAiklEQVQ4T9VTuQ3AIAzE01GwC8OwC4WnIzokRxhCZJwqLpD/96DgoBhjY2ZCaH9AUAoPo8g7Hr6w9QS11lZKCTnnkFIiyNDvePiqDiSBKN+mGpPfI0CJao516B1YOpiLqB18SvCvERYgnVxhPrlComWJczHXFR5HADxP/sICZQm2/IXx5C74jmi8AKmHmBE9o3aPAAAAAElFTkSuQmCC" rel="icon" type="image/x-icon">

<script type="text/javascript" src="asciiflow/asciiflow.nocache.js"></script>

<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-23949519-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>

<%
	String adslot;
	long random = Math.round(Math.floor(Math.random()*4));
	if (random == 0) {
		adslot =  "left";
	} else if (random == 1) {
		adslot = "right";
	} else if (random == 2) {
		adslot = "bottom";
	} else {
		adslot = "top";
	}
%>

<script type="text/javascript" src="http://connect.facebook.net/en_US/all.js#appId=179968592059905&amp;xfbml=1"></script>

<script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>

<script type="text/javascript" src="https://apis.google.com/js/plusone.js"></script>

</head>

<body>

<table style="display: inline-block;">
	<tr>
		<td colspan="3">
<% if ("top".equals(adslot)) { %>
<script type="text/javascript"><!--
	google_ad_client = "ca-pub-5033805476723981";
	/* Links */
	google_ad_slot = "2167957931";
	google_ad_width = 728;
	google_ad_height = 15;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
<% } %>
		</td>
	</tr>
	<tr>
		<td>
<% if ("left".equals(adslot)) { %>
<script type="text/javascript"><!--
	google_ad_client = "ca-pub-5033805476723981";
	/* Left */
	google_ad_slot = "3046880976";
	google_ad_width = 120;
	google_ad_height = 600;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
<% } %>
		</td>
		<td>
			<div id="main"></div>
		</td>
		<td>
<% if ("right".equals(adslot)) { %>
<script type="text/javascript"><!--
	google_ad_client = "ca-pub-5033805476723981";
	/* Right */
	google_ad_slot = "6855894996";
	google_ad_width = 120;
	google_ad_height = 600;
//-->
</script>
<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
<% } %>
		</td>
	</tr>
	<tr>
		<td colspan="3">
<% if ("bottom".equals(adslot)) { %>
<script type="text/javascript"><!--
	google_ad_client = "ca-pub-5033805476723981";
	/* Bottom */
	google_ad_slot = "6181120069";
	google_ad_width = 728;
	google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
<% } %>
		</td>
	</tr>
</table>

<div id="fb-root"></div>

<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
	style="position: absolute; width: 0; height: 0; border: 0"></iframe>

<noscript>
<div
	style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
Please turn on JavaScript in order to use this site!</div>
</noscript>

</body>
</html>
