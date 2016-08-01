<?php
	
	require_once 'key.php';
	require_once 'connect.php';
		
	function begin() {
		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
		
		$md5s = explode(",", $uConnect->real_escape_string($_POST['Injected']));
		$name = $uConnect->real_escape_string($_POST['Name']);
		$hardwareid = $uConnect->real_escape_string($_POST['HardwareID']);
		$hardwareid2 = $uConnect->real_escape_string($_POST['HardwareID2']);
		
		$asi_md5 = 'a';
		if($result = $uConnect->query("SELECT `hash` FROM `AC_Hashes` WHERE `filename` = 'samp.dll' LIMIT 1")) {
			if($row = $result->fetch_assoc()) {
				$sampdll = explode(",", $row['hash']);
			}
			$result->free();
		}
		
		if($result = $uConnect->query("SELECT `hash` FROM `AC_Hashes` WHERE `filename` = 'ac.asi' LIMIT 1")) {
			if($row = $result->fetch_assoc()) {
				$asi_md5 = $row['hash'];
			}
			$result->free();
		}
		$foundasi = false;
		
		if($result = $uConnect->query("SELECT `Link`, `Link_InstallerUpdate` FROM `AC_Info` WHERE `Type` = 'InjectedMD5s' LIMIT 1")) {
			if($row = $result->fetch_assoc()) {
				$badmd5s = explode(",", $row['Link']);
				$badmd5s_temp = explode(",", $row['Link_InstallerUpdate']);
				for($i = 0; $i < count($md5s); $i++) {
					
					if(!strcmp($md5s[$i], $asi_md5)) {
						$foundasi = true;
						continue;
					}
					for($l = 0; $l < count($sampdll); $l++) {
						if(!strcmp($md5s[$i], $sampdll[$l])) {
							if($foundasi == false) {
								echo 'notinjectedprop';
								$uConnect->close();
								exit();
								return 1;
							}
							continue;
						}
					}
					for($k = 0; $k < count($badmd5s); $k++) {
						if(!strcmp($badmd5s[$k], $md5s[$i]) && strlen($badmd5s[$k]) > 0 && strlen($md5s[$i]) > 0) {
							require_once 'ban_me_no_key.php';
							$md5 = $md5s[$i];
							$processes = explode(",", $uConnect->real_escape_string($_POST['fnames']));
							$reason = '' . $md5 . ' - ' . $processes[$i];
							ban_bro($reason);
							echo 'banned';
							break;
						}
					} 
					
					for($j = 0; $j < count($badmd5s_temp); $j++) {
						if(!strcmp($badmd5s_temp[$j], $md5s[$i]) && strlen($badmd5s_temp[$j]) > 0 && strlen($md5s[$i]) > 0) {
							require_once 'ban_me_no_key.php';
							$md5 = $md5s[$i];
							$processes = explode(",", $uConnect->real_escape_string($_POST['fnames']));
							$reason = '' . $md5 . ' - ' . $processes[$i];
							ban_bro($reason);
							echo 'banned';
							break;
						}
					}
				}
			}
		}
		if($foundasi == true) {
			echo 'it\'s all good';
		} else { echo 'notinjected'; }
		
		include 'mysql_check_time.php';
		updatedb($uConnect, $uConnect->real_escape_string($_POST['IP']));
		$uConnect->close();
		exit();
	}
?>