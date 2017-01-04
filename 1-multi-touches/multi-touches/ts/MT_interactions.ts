import { FSM } from "./FSM";
import * as transfo from "./transfo";

function multiTouch(element: HTMLElement) : void {
    let pointerId_1 : number, Pt1_coord_element : SVGPoint, Pt1_coord_parent : SVGPoint,
        pointerId_2 : number, Pt2_coord_element : SVGPoint, Pt2_coord_parent : SVGPoint,
        originalMatrix : SVGMatrix,
        getRelevantDataFromEvent = (evt : TouchEvent) : Touch => {
            for(let i=0; i<evt.changedTouches.length; i++) {
                let touch = evt.changedTouches.item(i);
                if(touch.identifier === pointerId_1 || touch.identifier === pointerId_2) {
                    return touch;
                }
            }
            return null;
        };
    enum MT_STATES {Inactive, Translating, Rotozooming}
    let fsm = FSM.parse<MT_STATES>( {
        initialState: MT_STATES.Inactive,
        states: [MT_STATES.Inactive, MT_STATES.Translating, MT_STATES.Rotozooming],
        transitions : [
            { from: MT_STATES.Inactive, to: MT_STATES.Translating,
                eventTargets: [element],
                eventName: ["touchstart"],
                useCapture: false,
                action: (evt : TouchEvent) : boolean => {
                    console.log("Inactive to Translating");

                    var elementPosition = element.getBoundingClientRect();

                    console.log(elementPosition.left + " : " + elementPosition.top);
                    // Position of point relative to the image
                    Pt1_coord_element = transfo.getPoint(evt.touches[0].pageX - elementPosition.left, evt.touches[0].pageY - elementPosition.top);

                    originalMatrix = transfo.getMatrixFromElement(element);

                    return true;
                }
            },
            { from: MT_STATES.Translating, to: MT_STATES.Translating,
                eventTargets: [document],
                eventName: ["touchmove"],
                useCapture: true,
                action: (evt : TouchEvent) : boolean => {
                    evt.preventDefault();
                    evt.stopPropagation();

                    console.log("Translating to Translating");

                    // Position of point relative to the image
                    Pt1_coord_parent = transfo.getPoint(evt.touches[0].pageX, evt.touches[0].pageY);

                    transfo.drag(element, originalMatrix, Pt1_coord_element, Pt1_coord_parent);

                    return true;
                }
            },
            { from: MT_STATES.Translating,
                to: MT_STATES.Inactive,
                eventTargets: [document],
                eventName: ["touchend"],
                useCapture: true,
                action: (evt : TouchEvent) : boolean => {

                    console.log("Translating to Inactive");
                    return true;
                }
            },
            { from: MT_STATES.Translating, to: MT_STATES.Rotozooming,
                eventTargets: [element],
                eventName: ["touchstart"],
                useCapture: false,
                action: (evt : TouchEvent) : boolean => {
                    console.log("Translating to Rotozooming");

                    var elementPosition = element.getBoundingClientRect();

                    console.log(elementPosition.left + " : " + elementPosition.top);

                    // Position of point relative to the image
                    Pt2_coord_element = transfo.getPoint(evt.touches[0].pageX - elementPosition.left, evt.touches[0].pageY - elementPosition.top);

                    Pt1_coord_parent = Pt1_coord_element;
                    Pt2_coord_parent = Pt2_coord_element;
                    return true;
                }
            },
            { from: MT_STATES.Rotozooming, to: MT_STATES.Rotozooming,
                eventTargets: [document],
                eventName: ["touchmove"],
                useCapture: true,
                action: (evt : TouchEvent) : boolean => {
                    evt.preventDefault();
                    evt.stopPropagation();
                    console.log("Rotozooming to Rotozooming");
                    /* Retrieve new X and new Y */
                    var cordX = evt.touches[0].pageX;
                    var cordY = evt.touches[0].pageY;

                    /* NOTE : Here, we always asume that the second finger was moved (because it is the only thing we can test). We would normally have to first decide based on cordX,cordY and PT1 et PT2 which finger was moved */
                    Pt2_coord_parent = transfo.getPoint(cordX, cordY);

                    transfo.rotozoom(element, originalMatrix, Pt1_coord_element, Pt1_coord_parent, Pt2_coord_element, Pt2_coord_parent);
                    return true;
                }
            },
            { from: MT_STATES.Rotozooming,
                to: MT_STATES.Translating,
                eventTargets: [document],
                eventName: ["touchend"],
                useCapture: true,
                action: (evt : TouchEvent) : boolean => {
                    let touch = getRelevantDataFromEvent(evt);
                    console.log("Rotozooming to Translating");

                    /* Exactly the same thing that happens when going from Inative to Translating */
                    var elementPosition = element.getBoundingClientRect();

                    console.log(elementPosition.left + " : " + elementPosition.top);
                    // Position of point relative to the image
                    Pt1_coord_element = transfo.getPoint(evt.touches[0].pageX - elementPosition.left, evt.touches[0].pageY - elementPosition.top);

                    originalMatrix = transfo.getMatrixFromElement(element);

                    return true;
                }
            }
        ]
    } );
    fsm.start();
}

//______________________________________________________________________________________________________________________
//______________________________________________________________________________________________________________________
//______________________________________________________________________________________________________________________
function isString(s : any) : boolean {
    return typeof(s) === "string" || s instanceof String;
}

export let $ = (sel : string | Element | Element[]) : void => {
    let L : Element[] = [];
    if( isString(sel) ) {
        L = Array.from( document.querySelectorAll(<string>sel) );
    } else if(sel instanceof Element) {
        L.push( sel );
    } else if(sel instanceof Array) {
        L = sel;
    }
    L.forEach( multiTouch );
};
