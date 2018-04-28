package cz.muni.fi.hrm.dto;

public class ResultDTO {
    public Integer matchInPerc;
    public Integer pointsFitInMargin;
    public Integer numberOfPoints;

    public ResultDTO(){
        this(null, null, null);
    }

    public ResultDTO(Integer matchInPerc, Integer pointsFitInMargin, Integer numberOfPoints){
        this.matchInPerc = matchInPerc;
        this.pointsFitInMargin = pointsFitInMargin;
        this.numberOfPoints = numberOfPoints;
    }

    public Integer getMatchInPerc() {
        return matchInPerc;
    }

    public void setMatchInPerc(Integer matchInPerc) {
        this.matchInPerc = matchInPerc;
    }

    public void setPointsFitInMargin(Integer pointsFitInMargin) {
        this.pointsFitInMargin = pointsFitInMargin;
    }

    public Integer getPointsFitInMargin() {
        return pointsFitInMargin;
    }

    public void setNumberOfPoints(Integer numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public Integer getNumberOfPoints() {
        return numberOfPoints;
    }
}
