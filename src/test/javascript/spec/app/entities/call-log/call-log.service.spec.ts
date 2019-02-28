/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CallLogService } from 'app/entities/call-log/call-log.service';
import { ICallLog, CallLog } from 'app/shared/model/call-log.model';

describe('Service Tests', () => {
    describe('CallLog Service', () => {
        let injector: TestBed;
        let service: CallLogService;
        let httpMock: HttpTestingController;
        let elemDefault: ICallLog;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CallLogService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CallLog(0, 0, 'AAAAAAA', currentDate, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        callLogDate: currentDate.format(DATE_FORMAT)
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

            it('should create a CallLog', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        callLogDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        callLogDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CallLog(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CallLog', async () => {
                const returnedFromService = Object.assign(
                    {
                        callLogId: 1,
                        callLogComment: 'BBBBBB',
                        callLogDate: currentDate.format(DATE_FORMAT),
                        callLogContactMail: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        callLogDate: currentDate
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

            it('should return a list of CallLog', async () => {
                const returnedFromService = Object.assign(
                    {
                        callLogId: 1,
                        callLogComment: 'BBBBBB',
                        callLogDate: currentDate.format(DATE_FORMAT),
                        callLogContactMail: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        callLogDate: currentDate
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

            it('should delete a CallLog', async () => {
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
