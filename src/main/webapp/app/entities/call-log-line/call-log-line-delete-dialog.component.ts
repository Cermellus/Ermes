import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICallLogLine } from 'app/shared/model/call-log-line.model';
import { CallLogLineService } from './call-log-line.service';

@Component({
    selector: 'jhi-call-log-line-delete-dialog',
    templateUrl: './call-log-line-delete-dialog.component.html'
})
export class CallLogLineDeleteDialogComponent {
    callLogLine: ICallLogLine;

    constructor(
        protected callLogLineService: CallLogLineService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.callLogLineService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'callLogLineListModification',
                content: 'Deleted an callLogLine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-call-log-line-delete-popup',
    template: ''
})
export class CallLogLineDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ callLogLine }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CallLogLineDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.callLogLine = callLogLine;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/call-log-line', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/call-log-line', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
