<?php
	
	require_once 'key.php';
	require_once 'connect.php';
		
	function begin() {		
		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);		
		
		$asi = $uConnect->real_escape_string($_POST['ASI']);
		$trainer = $uConnect->real_escape_string($_POST['Trainer']);
		$connectedip = $uConnect->real_escape_string($_POST['ConnectedIP']);
		$file_0 = $uConnect->real_escape_string($_POST['file_0']);
		$file_1 = $uConnect->real_escape_string($_POST['file_1']);
		$file_2 = $uConnect->real_escape_string($_POST['file_2']);
		$file_3 = $uConnect->real_escape_string($_POST['file_3']);
		$file_4 = $uConnect->real_escape_string($_POST['file_4']);
		$file_5 = $uConnect->real_escape_string($_POST['file_5']);
		$file_6 = $uConnect->real_escape_string($_POST['file_6']);
		$file_7 = $uConnect->real_escape_string($_POST['file_7']);
		$file_8 = $uConnect->real_escape_string($_POST['file_8']);
		$file_9 = $uConnect->real_escape_string($_POST['file_9']);
		$file_10 = $uConnect->real_escape_string($_POST['file_10']);
		$file_11 = $uConnect->real_escape_string($_POST['file_11']);
		$file_12 = $uConnect->real_escape_string($_POST['file_12']);
		$file_13 = $uConnect->real_escape_string($_POST['file_13']);
		$file_14 = $uConnect->real_escape_string($_POST['file_14']);
		$file_15 = $uConnect->real_escape_string($_POST['file_15']);
		$file_16 = $uConnect->real_escape_string($_POST['file_16']);
		$file_17 = $uConnect->real_escape_string($_POST['file_17']);
		$file_18 = $uConnect->real_escape_string($_POST['file_18']);
		$file_19 = $uConnect->real_escape_string($_POST['file_19']);
		$file_20 = $uConnect->real_escape_string($_POST['file_20']);
		$file_21 = $uConnect->real_escape_string($_POST['file_21']);
		$file_22 = $uConnect->real_escape_string($_POST['file_22']);
		$file_23 = $uConnect->real_escape_string($_POST['file_23']);
		$file_24 = $uConnect->real_escape_string($_POST['file_24']);
		$file_25 = $uConnect->real_escape_string($_POST['file_25']);
		$file_26 = $uConnect->real_escape_string($_POST['file_26']);
		$file_27 = $uConnect->real_escape_string($_POST['file_27']);
		$file_28 = $uConnect->real_escape_string($_POST['file_28']);
		$file_29 = $uConnect->real_escape_string($_POST['file_29']);
		$file_30 = $uConnect->real_escape_string($_POST['file_30']);
		$ip = $uConnect->real_escape_string($_POST['IP']);
		
		$uConnect->query("UPDATE `AC_Players` SET `HasASI` = '" . $asi . "', HasTrainer = '" . $trainer . "', `AppRunning` = 1, ConnectedIP = '" . $connectedip . "', `NextCheck` = UNIX_TIMESTAMP() + 80, file_0 = '" . $file_0 . "', file_1 = '" . $file_1 . "', file_2 = '" . $file_2 . "', file_3 = '" . $file_3 . "', file_4 = '" . $file_4 . "', file_5 = '" . $file_5 . "',  file_6 = '" . $file_6 . "', file_7 = '" . $file_7 . "', file_8 = '" . $file_8 . "', file_9 = '" . $file_9 . "', file_10 = '" . $file_10 . "', file_11 = '" . $file_11 . "', file_12 = '" . $file_12 . "', file_13 = '" . $file_13 . "', file_14 = '" . $file_14 . "', file_15 = '" . $file_15 . "', file_16 = '" . $file_16 . "', file_17 = '" . $file_17 . "', file_18 = '" . $file_18 . "', file_19 = '" . $file_19 . "', file_20 = '" . $file_20 . "', file_21 = '" . $file_21 . "', file_22 = '" . $file_22 . "', file_23 = '" . $file_23 . "', file_24 = '" . $file_24 . "', file_25 = '" . $file_25 . "', file_26 = '" . $file_26 . "', file_27 = '" . $file_27 . "', file_28 = '" . $file_28 . "', file_29 = '" . $file_29 . "', file_30 = '" . $file_30 . "' WHERE `IP` = '" . $ip . "'");
		
		$uConnect->close();
		echo 'OK';
		exit();
	}

	
?>
