/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ErmesTestModule } from '../../../test.module';
import { CallLogComponent } from 'app/entities/call-log/call-log.component';
import { CallLogService } from 'app/entities/call-log/call-log.service';
import { CallLog } from 'app/shared/model/call-log.model';

describe('Component Tests', () => {
    describe('CallLog Management Component', () => {
        let comp: CallLogComponent;
        let fixture: ComponentFixture<CallLogComponent>;
        let service: CallLogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CallLogComponent],
                providers: []
            })
                .overrideTemplate(CallLogComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CallLogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CallLogService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CallLog(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.callLogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
