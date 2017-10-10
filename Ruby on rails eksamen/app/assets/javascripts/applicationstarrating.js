/**
 * Created by aodegaar on 19.05.2017.
 */

var set_stars = function(form_id, stars) {
    for (var i=1; i <= 6; i++){
        if(i <= stars ) {
            $('#' + form_id + '_' + i).addClass('glyphicon-star text-star').removeClass('glyphicon-star-empty');
        } else {
            $('#' + form_id + '_' + i).removeClass('glyphicon-star text-star').addClass('glyphicon-star-empty');
        }
    }
};


$(document).on('turbolinks:load', function() {
    $('.glyphicon-star-empty').click(function () {
        var form_id = $(this).attr('data-form-id');
        var stars = $(this).attr('data-stars');

        set_stars(form_id, stars);

        $('#' + form_id + '_stars').val(stars);

        $.ajax({
            type: "post",
            url: $('#' + form_id).attr('action'),
            data: $('#' + form_id).serialize()
        });

        $('#average_' + form_id).load(window.location.href + ' #average_' + form_id);

    });

    $('.star_rating_form').each(function() {

        var form_id = $(this).attr('id');
        var stars = $('#' + form_id + '_stars').val();
        set_stars(form_id, stars);
    });
});