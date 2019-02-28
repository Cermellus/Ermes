/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { CreditReasonService } from 'app/entities/credit-reason/credit-reason.service';
import { ICreditReason, CreditReason } from 'app/shared/model/credit-reason.model';

describe('Service Tests', () => {
    describe('CreditReason Service', () => {
        let injector: TestBed;
        let service: CreditReasonService;
        let httpMock: HttpTestingController;
        let elemDefault: ICreditReason;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CreditReasonService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new CreditReason(0, 0, 'AAAAAAA', 'AAAAAAA', false, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a CreditReason', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new CreditReason(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CreditReason', async () => {
                const returnedFromService = Object.assign(
                    {
                        creditReasonId: 1,
                        creditReasonDescription: 'BBBBBB',
                        creditReasonCode: 'BBBBBB',
                        creditReasonProductRequired: true,
                        creditReasonCourierClaim: true
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of CreditReason', async () => {
                const returnedFromService = Object.assign(
                    {
                        creditReasonId: 1,
                        creditReasonDescription: 'BBBBBB',
                        creditReasonCode: 'BBBBBB',
                        creditReasonProductRequired: true,
                        creditReasonCourierClaim: true
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
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

            it('should delete a CreditReason', async () => {
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
