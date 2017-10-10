/**
 * Created by aodegaar on 17.05.2017.
 */

$(document).on('turbolinks:load', function() {

    $("#loginButton").click(function(){
        $("#login_modal").modal();
    });
    $('#submit_login').click(function() {
        $('#login_modal').modal('hide');
    });

});

$(document).on('turbolinks:load', function() {

    $("#sell_book_btn").click(function(){
        $("#sell_book_modal").modal();
    });
    $('#submit_sell_book').click(function() {
        $('#sell_book_modal').modal('hide');
    });

});

$(document).on('turbolinks:load', function() {

    $("[data-collapse-group='collapseButtons']").click(function () {
        var $this = $(this);
        $("[data-collapse-group='collapseButtons']:not([data-target='" + $this.data("target") + "'])").each(function () {
            $($(this).data("target")).removeClass("in").addClass('collapse');
        });
    });

});