"use strict";

function Abstract() {
    this.ar = arguments;
    this.stringBuilder = function () {
        var string = "";
        for (var i = 0; i < arguments.length - 1; i++) {
            string += arguments[i].toString() + " ";
        }
        return string + arguments[i];
    };
    this.prefStringBuilder = function () {
        var string = (arguments.length > 1) ? "(" + arguments[0] + " " : "" + arguments[0];
        for (var i = 1; i < arguments.length - 1; i++) {
            string += arguments[i].prefix() + " ";
        }
        return (arguments.length > 1) ? string + arguments[i].prefix() + ")" : string;
    };
}

Abstract.prototype.evaluate = function () {
    if (this.ar.length === 2) {
        return this.operation(this.ar[0].evaluate.apply(this.ar[0], arguments), this.ar[1].evaluate.apply(this.ar[1], arguments));
    }
    if (this.ar.length === 1) {
        return this.operation(this.ar[0].evaluate.apply(this.ar[0], arguments));
    }
    if (this.ar.length === 0) {
        return this.operation(arguments);
    }
};

Abstract.prototype.prefix = function () {
    return this.prefString;
};

Abstract.prototype.toString = function () {
    return this.string;
};

var arr = ['x', 'y', 'z'];

function Const(x) {
    Abstract.call(this);
    this.operation = function () {
        return x;
    };
    this.string = this.stringBuilder(x);
    this.prefString = this.prefStringBuilder(x);
}

function Variable(x) {
    Abstract.call(this);
    this.operation = function () {
        return arguments[0][arr.indexOf(x)];
    };
    this.string = this.stringBuilder(x);
    this.prefString = this.prefStringBuilder(x);
}

function Add(x, y) {
    Abstract.call(this, x, y);
    this.operation = function (a, b) {
        return a + b;
    };
    this.string = this.stringBuilder(x, y, "+");
    this.prefString = this.prefStringBuilder("+", x, y);
}

function Subtract(x, y) {
    Abstract.call(this, x, y);
    this.operation = function (a, b) {
        return a - b;
    };
    this.string = this.stringBuilder(x, y, "-");
    this.prefString = this.prefStringBuilder("-", x, y);
}

function Multiply(x, y) {
    Abstract.call(this, x, y);
    this.operation = function (a, b) {
        return a * b;
    };
    this.string = this.stringBuilder(x, y, "*");
    this.prefString = this.prefStringBuilder("*", x, y);
}

function Divide(x, y) {
    Abstract.call(this, x, y);
    this.operation = function (a, b) {
        return a / b;
    };
    this.string = this.stringBuilder(x, y, "/");
    this.prefString = this.prefStringBuilder("/", x, y);
}

function Negate(x) {
    Abstract.call(this, x);
    this.operation = function (a) {
        return -1 * a;
    };
    this.string = this.stringBuilder(x, "negate");
    this.prefString = this.prefStringBuilder("negate", x);
}

function Sinh(x) {
    Abstract.call(this, x);
    this.operation = function (a) {
        return (Math.pow(Math.E, a) - Math.pow(Math.E, -1 * a)) / 2;
    };
    this.string = this.stringBuilder(x, "sinh");
    this.prefString = this.prefStringBuilder("sinh", x);
}

function Cosh(x) {
    Abstract.call(this, x);
    this.operation = function (a) {
        return (Math.pow(Math.E, a) + Math.pow(Math.E, -1 * a)) / 2;
    };
    this.string = this.stringBuilder(x, "cosh");
    this.prefString = this.prefStringBuilder("cosh", x);
}

function Power(x, y) {
    Abstract.call(this, x, y);
    this.operation = function (a, b) {
        return Math.pow(a, b);
    };
    this.string = this.stringBuilder(x, y, "pow");
    this.prefString = this.prefStringBuilder("pow", x, y);
}

function Log(x, y) {
    Abstract.call(this, x, y);
    this.operation = function (a, b) {
        return Math.log(Math.abs(b)) / Math.log(Math.abs(a));
    };
    this.string = this.stringBuilder(x, y, "log");
    this.prefString = this.prefStringBuilder("log", x, y);
}

var ptr = [Negate, Add, Const, Variable, Subtract, Multiply, Divide, Sinh, Cosh, Power, Log];
for (var i = 0; i < ptr.length; i++) {
    ptr[i].prototype = Object.create(Abstract.prototype);
}

var expr = new Divide(new Variable('x'), new Multiply(new Variable('y'), new Variable('z')));
println(expr.prefix());