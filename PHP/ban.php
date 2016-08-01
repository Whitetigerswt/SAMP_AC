<?php
    require_once 'connect.php';
    $uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
 
    if (mysqli_connect_errno()) { 
		$uConnect = new mysqli(mysql_host, mysql_user2, mysql_pw2, mysql_db);
		if (mysqli_connect_errno()) { 
			$uConnect = new mysqli(mysql_host, mysql_user3, mysql_pw3, mysql_db);
			if (mysqli_connect_errno()) { 
				die("Could not connect to the DB"); 
				return 1;
			}
		}
	}
    $EscIP = $uConnect->real_escape_string($_POST['IP']);
	
	if(!strlen($EscIP)) { }	
 
    else if($result = $uConnect->query("SELECT *, UNIX_TIMESTAMP() AS `unix_timestamp` FROM `AC_Bans` WHERE `IP` = '" . $EscIP . "' LIMIT 1"))
    {
        $row = $result->fetch_assoc();
		
		if(!strcmp($row['IP'], $EscIP) && ($row['Timestamp'] > $row['unix_timestamp'] || $row['Timestamp'] == 0)) {
			echo '1';
		} else {
			echo '0';
		}
 
        $result->free();
    } else echo '0';
     
    $uConnect->Close();
?>