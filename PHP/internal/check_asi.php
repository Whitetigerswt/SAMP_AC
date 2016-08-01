<?php
	
	require_once 'key.php';
	require_once 'connect.php';
		
	function begin() {
		
		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
		
		$md5s = $uConnect->real_escape_string($_POST['ASI']);
		$name = $uConnect->real_escape_string($_POST['Name']);
		$ip = $uConnect->real_escape_string($_POST['IP']);
		
		if($result = $uConnect->query("SELECT `Link`, `Link_InstallerUpdate` FROM `AC_Info` WHERE `Type` = 'ASIMD5s' LIMIT 1")) {
			if($row = $result->fetch_assoc()) {
				$badmd5s = explode(",", $row['Link']);
				$goodmd5s = explode(",", $row['Link_InstallerUpdate']);
				
				$matches = false;
				for($k = 0; $k < count($badmd5s); $k++) {

					if(!strcmp($badmd5s[$k], $md5s) && strlen($badmd5s[$k]) > 0 && strlen($md5s) > 0) {
						echo 'it\'s all good';
						$matches = true;
						$result->free();
						$uConnect->close();
						exit();
						return 1;
					}
					
					
				}
				if(!$matches) {
					echo 'upload';
					$uConnect->query("UPDATE `AC_Players` SET `HasASI` = 'true' WHERE `IP` = '" . $ip . "'");
					$result->free();
					$uConnect->close();
					exit();
					return 1;
				}
			}
			$result->free();
		}
		echo 'it\'s all good';
		$uConnect->close();
		exit();
	}
?>