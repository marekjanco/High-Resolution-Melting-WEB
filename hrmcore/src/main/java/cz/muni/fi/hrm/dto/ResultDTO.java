package cz.muni.fi.hrm.dto;

public class ResultDTO {
    int matchInPerc;
    int pointsFitInMargin;
    int numberOfPoints;

    public ResultDTO(int matchInPerc, int pointsFitInMargin, int numberOfPoints){
        this.matchInPerc = matchInPerc;
        this.pointsFitInMargin = pointsFitInMargin;
        this.numberOfPoints = numberOfPoints;
    }
}
