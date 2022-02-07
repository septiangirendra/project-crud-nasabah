<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable 
		$id = $_POST['id'];
		$nama = $_POST['nama'];
		$no_rekening = $_POST['no_rekening'];
		$tabungan = $_POST['tabungan'];
		$saldo = $_POST['saldo'];
		
		//import file koneksi database 
		require_once('koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE tb_nasabah SET nama = '$nama', no_rekening = '$no_rekening', 
		tabungan = '$tabungan', saldo = '$saldo' WHERE id = $id;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Nasabah';
		}else{
			echo 'Gagal Update Data Nasabah';
		}
		
		mysqli_close($con);
	}
?>