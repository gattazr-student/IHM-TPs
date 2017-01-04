let re_matrix = /^matrix\((.*), (.*), (.*), (.*), (.*), (.*)\)$/;

let svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
let idM	= svg.createSVGMatrix();
idM.a=1; idM.b=0; idM.c=0; idM.d=1; idM.e=0; idM.f=0;

//______________________________________________________________________________________________________________________
export let setMatrixCoordToElement =    ( element: HTMLElement
                                        , a : number
                                        , b : number
                                        , c : number
                                        , d : number
                                        , e : number
                                        , f : number
                                        ) => {
    element.style.transform = "matrix(" + a +"," + b +"," + c +"," + d +"," + e +"," + f +")";
};

//______________________________________________________________________________________________________________________
export let setMatrixToElement = (element: HTMLElement, M: SVGMatrix) => {
    setMatrixCoordToElement(element, M.a, M.b, M.c, M.d, M.e, M.f);
};

//______________________________________________________________________________________________________________________
export let getMatrixFromString = (str: string) : SVGMatrix => {
    let res		= re_matrix.exec( str )
      , matrix	= svg.createSVGMatrix()
      ;
    matrix.a = parseFloat(res[1]) || 1;
    matrix.b = parseFloat(res[2]) || 0;
    matrix.c = parseFloat(res[3]) || 0;
    matrix.d = parseFloat(res[4]) || 1;
    matrix.e = parseFloat(res[5]) || 0;
    matrix.f = parseFloat(res[6]) || 0;

    return matrix;
};

//______________________________________________________________________________________________________________________
export let getPoint = (x: number, y: number) : SVGPoint => {
    let point = svg.createSVGPoint();
    point.x = x || 0;
    point.y = y || 0;
    return point;
};

//______________________________________________________________________________________________________________________
export let getMatrixFromElement = (element: Element) : SVGMatrix => {
	return getMatrixFromString( window.getComputedStyle(element).transform || "matrix(1,1,1,1,1,1)" );
};

//______________________________________________________________________________________________________________________
/*
 * element : html element to drag
 * originalMatrix : transformation matrix (to calculate)
 * Pt_coord_element : initial point
 * Pt_coord_parent : end point
 */
export let drag =       ( element               : HTMLElement
                        , originalMatrix        : SVGMatrix
                        , Pt_coord_element      : SVGPoint
                        , Pt_coord_parent       : SVGPoint
                        ) => {
    originalMatrix.e = Pt_coord_parent.x - originalMatrix.a * Pt_coord_element.x - originalMatrix.c * Pt_coord_element.y ;
    originalMatrix.f = Pt_coord_parent.y - originalMatrix.b * Pt_coord_element.x - originalMatrix.d * Pt_coord_element.y ;

    setMatrixToElement(element, originalMatrix);
};

//______________________________________________________________________________________________________________________
/*
 * element : html element to drag
 * originalMatrix : transformation matrix (to calculate)
 * Pt1_coord_element : point1 to move
 * Pt1_coord_parent : arrival point1
 * Pt2_coord_element : point2 to move
 * Pt2_coord_parent : arrival point2
 */
export let rotozoom =   ( element           : HTMLElement
                        , originalMatrix    : SVGMatrix
                        , Pt1_coord_element : SVGPoint
                        , Pt1_coord_parent  : SVGPoint
                        , Pt2_coord_element : SVGPoint
                        , Pt2_coord_parent  : SVGPoint
                        ) => {
	// SVGMatrix vers originalMatrix
    // a -> c
    // b -> s
    // c -> -s
    // d -> c
    // e -> f
    // f -> f
    let dxElement = Pt2_coord_element.x - Pt1_coord_element.x;
    let dyElement = Pt2_coord_element.y - Pt1_coord_element.y;
    let dxParent = Pt2_coord_parent.x - Pt1_coord_parent.x;
    let dyParent = Pt2_coord_parent.y - Pt1_coord_parent.y;

    if (dxElement == 0 && dyElement != 0){
        originalMatrix.b = -dxParent / dyElement;
        originalMatrix.c = -originalMatrix.b;

        originalMatrix.d = dyParent / dyElement;
        originalMatrix.a = originalMatrix.d;
    }else if (dxElement != 0 && dyElement == 0){
        originalMatrix.b = dyParent / dxElement;
        originalMatrix.c = -originalMatrix.b;

        originalMatrix.d = dxParent / dxElement;
        originalMatrix.a = originalMatrix.d;
    }else if (dxElement != 0 && dyElement != 0){
        originalMatrix.b = (dyParent / dyElement - dxParent/dxElement) / (dyElement / dxElement + dxElement / dyElement);
        originalMatrix.c = -originalMatrix.b;

        originalMatrix.d = (dyParent - originalMatrix.b * dxElement) / dyElement;
        originalMatrix.a = originalMatrix.d;
    }else{
        return;
    }

    originalMatrix.e = Pt1_coord_parent.x - originalMatrix.d * Pt1_coord_element.x + originalMatrix.b * Pt1_coord_element.y;
    originalMatrix.f = Pt1_coord_parent.y - originalMatrix.b * Pt1_coord_element.x - originalMatrix.d * Pt1_coord_element.y;


    setMatrixToElement(element, originalMatrix);
};
