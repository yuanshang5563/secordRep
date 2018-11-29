function  getIsIE() {
	if (!!window["ActiveXObject"] || "ActiveXObject" in window){
		 return true;
	}else{
		return false;
	}
}