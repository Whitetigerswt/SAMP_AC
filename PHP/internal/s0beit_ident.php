<?php
	require_once 'connect.php';

	function mainfunc() {
		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
			
		$md5 = $uConnect->real_escape_string($_POST['md5']);
		
		if(strlen($md5) > 0) {
			if(strcmp($md5, "fa7be7cc1babf1bd7fc3b83f36cc6dd7") == 0) {
				$reason = '' . $md5 . ' - FYP m0d s0beit';
			} else if(strcmp($md5, "807a239ef6d010618eeb9db77d4839cd") == 0) {
				$reason = $md5 . ' - s0nictz s0beit Project';
			}
			
			
			else {
				require_once 'ban_me_no_key.php';
				$processes = explode(",", $uConnect->real_escape_string($_POST['fnames']));
				$reason = '' . $md5 . ' - Unknown s0beit version';
				ban_bro($reason);
				$uConnect->close();
				return 1;
			}
			
			require_once 'ban_me_no_key.php';
			ban_bro($reason);
			$uConnect->close();
			return 1;
		} else {
		
			require_once 'ban_me_no_key.php';
			$processes = explode(",", $uConnect->real_escape_string($_POST['fnames']));
			$reason = '' . $md5 . ' - Unknown s0beit version';
			ban_bro($reason);
		}
		$uConnect->close();
		return 1;		
	}
	
	mainfunc();
	exit();
	
?>
