(function ($) {
    //  inspired by DISQUS
    $.oauthpopup = function (options) {
        if (!options || !options.path) {
            throw new Error("options.path must not be empty");
        }
        options = $.extend({
            windowName: 'ConnectWithOAuth',
            windowOptions: 'modal,location=0,status=0,width=1020,height=590',
            callback: function () {
                window.location.reload();
            }
        }, options);

        var oauthWindow = window.open(options.path, options.windowName, 'modal,location=0,status=0,width=1020,height=590');

        console.log(oauthWindow);
        var oauthInterval = window.setInterval(function () {
            if (oauthWindow.closed) {
                console.log(oauthWindow.location);
                window.clearInterval(oauthInterval);
                options.callback(oauthWindow);
            }
        }, 1000);
    };

    //bind to element and pop oauth when clicked
    $.fn.oauthpopup = function (options) {
        $this = $(this);
        $this.click($.oauthpopup.bind(this, options));
    };
})(jQuery);