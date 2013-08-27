/*
 * jQuery Time Popup plugin v1.0
 * Autor: Pavel Grebennikov
 * Mail: nekwave@gmail.com
 * Script to create timepicker functionality with html.
 * Requires: jQuery v1.7+
*/
jQuery.fn.timepicker = function(options) {
    var translate = { // langs
        'ru': {
            alarm_comment:' - часы действия скидки'
        },
        'en': {
            alarm_comment:' - discount time'
        }
    }
    // default options
    var _options = {step:30, // time interval in minutes
                    at_row:6, // count intervals at row
                    active_from:'00:00', // time, from links are enabled
                    active_to:'00:00', // time, from links are disabled
                    alarm_from:'00:00', // time, from links are enabled and colored (example - discount work from 10am to 3pm) 
                    alarm_to:'00:00', // time, from colored links are disabled
                    alarm_color:'#e73626', // color of colored links
                    lang:'en', // lang
                    select_callback:'', // called whan user select enabled time interval link
                    open_callback:'', // called whan user click at input feld and select time
                    close_callback:''}; // called whan window close does not matter why
    /*
    * Input click action
    */
    
    if (typeof(options)=='string') {
        if (options == 'destroy') {
            this.each(function() {
                if ($(this).next().data('timepicker') === 1) {
                    $(this).next().remove();
                    $(this).parent().after(this);
                    $(this).prev().remove();
                    $(this).off('click',this.clickFunc);
                    $(document).off('click',this.documentClick)
                    $(this).removeClass('timepicker_input');
                }
                
            })
            return;
        }
    }
    _options = $.extend({},_options, options);
    
    /*
     * Need format 15:30
     */
    var timeToInt = function(time) {
        var tmp = time.split(':');
        return parseInt(tmp[0]*60)+parseInt(tmp[1]);
    }
    _options.active_from = timeToInt(_options.active_from);
    _options.active_to = timeToInt(_options.active_to);
    if (_options.active_from == 0 && _options.active_to == 0) { // if params set from 00:00 to 00:00 - seting -1 for disable
        _options.active_from = _options.active_to = -1;
    }
    
    _options.alarm_from = timeToInt(_options.alarm_from);
    _options.alarm_to = timeToInt(_options.alarm_to);
    if (_options.alarm_from == 0 && _options.alarm_to == 0) {
        _options.alarm_from = _options.alarm_to = -1;
    }  
    var html = new Array();
    html[0] = '<div data-timepicker="1" rel="timepickerWindow" style="position:absolute;z-index:9900;display:none;"><div class="reserve-time-dropdown"><table class="reserve-time-table">';
    html[1] = '';
    html[2] = '';
    this.each(function() {
        this.setPosition = function(element) { 
            $(element).css('left', 0 - element[0].clientWidth/2 + this.clientWidth/2  + 'px');
            $(element).css('top', this.offsetTop + (this.offsetHeight) + 6 + 'px');
        }
        var input = this; 
        
        /*
         * Create block width times intervals
         */
        this.create = function() {
            if ($(this).next().data('timepicker') === 1) {
                $(this).next().remove();
            }
            $(this).addClass('timepicker_input');
            $(this).after('<span class="timepicker-span"></span>').next().append(this).find('input');
            $(this).attr('autocomplete','off').attr('readonly','readonly');
            html[1] = '';
            var steps = 1440 / _options['step'];
            var classes = '';
            var selectedTime = timeToInt($(this).val());
            var date = new Date();
            var userTime = timeToInt(date.getHours()+':'+date.getMinutes());
            for (var a = 0; a < steps; a++) {
                if (a % _options['at_row'] == 0) {
                    if (a != 0) {
                        html[1] += '</tr>';
                    }
                    html[1] += '<tr>';
                }
                var now = a * _options['step'];
                var h = parseInt(now / 60);
                var m = (now % 60);
                if (h < 10)
                    h = '0' + h;
                if (m < 10)
                    m = '0' + m; 
                
                if (selectedTime == now) {
                    classes = 'active';
                } else {
                    if (userTime <= now && userTime+_options['step'] >= now) {
                        classes = 'user_active';
                    } else {
                        classes = '';
                    }
                }
                if (_options.alarm_from < _options.alarm_to) {
                    if (_options.alarm_from <= now && _options.alarm_to >= now && _options.alarm_from != -1) {
                        html[1] += '<td class="time-cell "><a class="possible-reserve-time '+classes+'" href="#" style="background:'+_options.alarm_color+'">' + h + '.' + m + '</a></td>';
                        continue;
                    }
                } else {
                    if ((_options.alarm_from <= now || _options.alarm_to >= now) && _options.alarm_from != -1) {
                        html[1] += '<td class="time-cell "><a class="possible-reserve-time '+classes+'" href="#" style="background:'+_options.alarm_color+'">' + h + '.'+m+'</a></td>';
                        continue;
                    }
                }
                if (_options.active_from < _options.active_to) {
                    if (_options.active_from <= now && _options.active_to >= now && _options.active_from != -1) {
                        html[1] += '<td class="time-cell"><a class=" '+classes+'" href="#">' + h + '.' + m + '</a></td>';
                        continue;
                    }
                } else {
                    if ((_options.active_from <= now || _options.active_to >= now) && _options.active_from != -1) {
                        html[1] += '<td class="time-cell"><a class=" '+classes+'" href="#">' + h + '.' + m + '</a></td>';
                        continue;
                    }
                } 
                html[1] += '<td class="time-cell"><a class="no-reserve-time '+classes+'" href="#">' + h + '.' + m + '</a></td>';
                
            }
            html[1] += '</tr>';
            $(this).after(html.join().replace(/,/g, '', 'g'));
            
            this.clickFunc = function() {
                if ($(input).next().css('display') == 'none') {
                    setTimeout(function() {
                        $(input).next().show();
                        input.setPosition($(input).next());
                        if (_options.open_callback) {
                            _options.open_callback(input);
                        }
                    }, 10)
                } else { 
                    $(input).next().hide();
                    if (_options.close_callback) {
                        _options.close_callback(input);
                    }
                }
            }
            $(this).next().find('a').click(function() {
                if ($(this).hasClass('no-reserve-time') || $(this).hasClass('example')) {
                    return false;
                }
                $(input).next().find('a').removeClass('active');
                $(this).addClass('active');
                if (_options.select_callback) {
                    _options.select_callback(this);
                }
                $(input).val($(this).text().replace('.', ':'));
                input.clickFunc();
                return false;
            });

            this.focused = 0;

            /*
             * Looking, whan mouse on block
             */
            $(this).next().mouseover(function() {
                input.focused = 1;
            }).mouseout(function() {
                input.focused = 0;
            });
            /*
             * Close block whan click outer of block
             */
            this.documentClick = function() {
                if (!input.focused) {
                    if ($(input).next().css('display') != 'none') {
                        $(input).next().hide();
                        if (_options.close_callback) {
                            _options.close_callback(input);
                        }
                    }
                }
            }
            $(document).on('click',this.documentClick)
            /*
             * Escape btn close block
             */
            $(document).keydown(function(e) {
                if (e.keyCode == 27) {
                    if ($(input).css('display') != 'none') {
                        $(input).next().hide();
                        if (_options.close_callback) {
                            _options.close_callback(input);
                        }
                    }
                }
            }); 
            
        }
        this.create();
        $(this).on('click',input.clickFunc);
    });
    return true;
}