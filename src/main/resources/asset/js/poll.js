(function(window, $) {
  $(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var poll_pattern = /poll-([\d]+)-([\d]+)/;
    var poll_interval = 1000;

    $(document).ajaxSend(function(e, xhr, options) {
      xhr.setRequestHeader(header, token);
    });

    $('#poll-options').on('click', 'li', function () {
      var option_id = $(this).attr('id');
      var match = poll_pattern.exec(option_id);

      $.post([match[1], match[2]].join('/'));
    });

    window.setTimeout(loadVotes, 100);

    function loadVotes() {
      return $.get(window.location.href + '/votes?_=' + Date.now())
        .then(updateVotes)
        .then(function(){
          window.setTimeout(loadVotes, poll_interval);
        }, function(){
          window.setTimeout(loadVotes, poll_interval);
        });
    }
    function updateVotes(data) {
      var $badges = $('#poll-options .badge');
      $badges.each(function () {
        var id = $(this).parent().attr('id').substring(7);
        $(this).text(data[id]);
      });
    }
  });
}(window, jQuery));
