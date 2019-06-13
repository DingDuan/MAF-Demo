function clickAll(){
        // if (this.checked) {
            $("input[name='selectFlag']:checkbox").each(function () { //遍历所有的name为selectFlag的 checkbox
                $(this).attr("checked", true);
            })
        // }
}

