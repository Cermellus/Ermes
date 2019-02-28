/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CreditRequestService } from 'app/entities/credit-request/credit-request.service';
import { ICreditRequest, CreditRequest } from 'app/shared/model/credit-request.model';

describe('Service Tests', () => {
    describe('CreditRequest Service', () => {
        let injector: TestBed;
        let service: CreditRequestService;
        let httpMock: HttpTestingController;
        let elemDefault: ICreditRequest;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CreditRequestService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CreditRequest(0, 0, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        creditRequestDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a CreditRequest', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        creditRequestDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        creditRequestDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CreditRequest(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CreditRequest', async () => {
                const returnedFromService = Object.assign(
                    {
                        creditRequestId: 1,
                        creditRequestDate: currentDate.format(DATE_FORMAT),
                        creditRequestCode: 'BBBBBB',
                        creditRequestReference: 'BBBBBB',
                        creditRequestContactMail: 'BBBBBB',
                        creditRequestRejectReason: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        creditRequestDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of CreditRequest', async () => {
                const returnedFromService = Object.assign(
                    {
                        creditRequestId: 1,
                        creditRequestDate: currentDate.format(DATE_FORMAT),
                        creditRequestCode: 'BBBBBB',
                        creditRequestReference: 'BBBBBB',
                        creditRequestContactMail: 'BBBBBB',
                        creditRequestRejectReason: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        creditRequestDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a CreditRequest', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
