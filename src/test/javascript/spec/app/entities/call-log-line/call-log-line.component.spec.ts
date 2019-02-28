/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ErmesTestModule } from '../../../test.module';
import { CallLogLineComponent } from 'app/entities/call-log-line/call-log-line.component';
import { CallLogLineService } from 'app/entities/call-log-line/call-log-line.service';
import { CallLogLine } from 'app/shared/model/call-log-line.model';

describe('Component Tests', () => {
    describe('CallLogLine Management Component', () => {
        let comp: CallLogLineComponent;
        let fixture: ComponentFixture<CallLogLineComponent>;
        let service: CallLogLineService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CallLogLineComponent],
                providers: []
            })
                .overrideTemplate(CallLogLineComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CallLogLineComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CallLogLineService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CallLogLine(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.callLogLines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
