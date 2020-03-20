import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ChallengeService } from 'app/entities/challenge/challenge.service';
import { IChallenge, Challenge } from 'app/shared/model/challenge.model';

describe('Service Tests', () => {
  describe('Challenge Service', () => {
    let injector: TestBed;
    let service: ChallengeService;
    let httpMock: HttpTestingController;
    let elemDefault: IChallenge;
    let expectedResult: IChallenge | IChallenge[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ChallengeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Challenge(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Challenge', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Challenge()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Challenge', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            problems: 'BBBBBB',
            description: 'BBBBBB',
            solution: 'BBBBBB',
            targetAudience: 'BBBBBB',
            stakeHolder: 'BBBBBB',
            slackChannel: 'BBBBBB',
            ministryProject: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Challenge', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            problems: 'BBBBBB',
            description: 'BBBBBB',
            solution: 'BBBBBB',
            targetAudience: 'BBBBBB',
            stakeHolder: 'BBBBBB',
            slackChannel: 'BBBBBB',
            ministryProject: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Challenge', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
