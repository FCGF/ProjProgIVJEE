  function isEmpty(str) {
        return (!str || 0 === str.length);
    }

    $(document).ready(function () {

        if (!isEmpty("${msg}")) {
            toastr['error']("${msg}");
        }

        $(".createButton").click(function () {
            alert("Create");
            window.location.href = "prog4/mvc?cmd=${cmd}&mtd=create";
        });

        $(".listButton").click(function () {
            alert("List");
            window.location.href = "prog4/mvc?cmd=${cmd}&mtd=list";
        });

        $(".editButton").click(function () {
            var id = $(this).data("id");
            alert("Update " + id);
            window.location.href = "prog4/mvc?cmd=${cmd}&mtd=update&id=" + id;
        });

        $(".detailsButton").click(function () {
            var id = $(this).data("id");
            alert("Detail " + id);
            window.location.href = "prog4/mvc?cmd=${cmd}&mtd=detail&id=" + id;
        });

        $(".deleteButton").click(function () {
            var id = $(this).data("id");
            var name = $(this).data("name");
            var link = "prog4/mvc?cmd=${cmd}&mtd=delete&id=" + id;
            alert(link);
            bootbox.confirm("Are you sure you wish to delete the element " + id + " - " + name + "?", function (result) {
                if (result) {
                    window.location.href = link;
                    alert("Deletar");
                }
            });
            
        });

    });