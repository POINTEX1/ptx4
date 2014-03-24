function disabledButtonAdd() {
    if (document.formAdd.btnAdd.disabled == false) {
        document.getElementById("formAdd").submit();
        document.formAdd.btnAdd.disabled = true;
        document.formAdd.btn1.hidden = true;
        document.formAdd.btn2.hidden = false;
    }
}